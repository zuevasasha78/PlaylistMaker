package com.example.playlistmaker.new.search.ui.activity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.api.TracksData
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.TrackConsumerImpl
import com.example.playlistmaker.domain.use_case.TracksHistoryInteractor
import com.example.playlistmaker.presentation.ui.audioplayer.AudioPlayerActivity
import com.example.playlistmaker.new.search.ui.activity.TrackAdapter
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySearchBinding

    private lateinit var tracksHistoryInteractor: TracksHistoryInteractor
    private val tracksInteractor = Creator.provideTracksInteractor()

    private lateinit var trackAdapter: TrackAdapter
    private val trackList = mutableListOf<Track>()
    private var trackListHistory = mutableListOf<Track>()
    private var savedText: String? = null
    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var searchRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.search) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createTrackHistoryInteractor()

        initTrackListView()

        viewBinding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    viewBinding.clearIcon.isVisible = true
                    showLoading()
                    searchRunnable = Runnable { searchRequest(s.toString()) }

                    handler.removeCallbacks(searchRunnable)
                    handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                savedText = s.toString()
            }
        })

        viewBinding.updateButton.setOnClickListener { view ->
            showLoading()
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
        }
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        viewBinding.clearHistory.setOnClickListener {
            trackListHistory = mutableListOf()
            tracksHistoryInteractor.clearTrackHistory()
            viewBinding.searchHistory.isVisible = false
        }

        viewBinding.clearIcon.setOnClickListener { v ->
            viewBinding.inputEditText.setText("")
            v.isVisible = false
            hideKeyboard()
            trackAdapter.setItems(trackListHistory)
            hideError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tracksHistoryInteractor.saveTrackHistory(trackListHistory)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText?.let { outState.putString(SEARCH_TEXT, savedText) }
    }

    private fun createTrackHistoryInteractor() {
        val provideSharedPreferences = Creator.provideSharedPreferences(applicationContext)
        val provideTracksInteractor = Creator.provideTracksHistoryRepository(provideSharedPreferences)
        tracksHistoryInteractor = Creator.provideTracksHistoryInteractor(provideTracksInteractor)
    }

    private fun showLoading() {
        viewBinding.progressBar.isVisible = true
        viewBinding.trackList.isVisible = false
        viewBinding.updateButton.isVisible = false
        viewBinding.searchHistory.isVisible = false
        hideError()
    }

    private fun startAudioPlayer(track: Track) {
        if (clickDebounce()) {
            val audioPlayerIntent = Intent(this, AudioPlayerActivity::class.java).apply {
                putExtra(TRACK_DATA, Gson().toJson(track))
            }
            startActivity(audioPlayerIntent)
        }
    }

    private fun searchRequest(searchText: String) {
        val collection = TrackConsumerImpl { dataTracks ->
            handler.post {
                trackList.clear()

                when (dataTracks) {
                    is TracksData.Success -> {
                        trackList.addAll(dataTracks.data)
                        trackAdapter.setItems(trackList)

                        viewBinding.trackList.isVisible = true
                        viewBinding.progressBar.isVisible = false
                        viewBinding.searchHistory.isVisible = false

                        trackAdapter.notifyDataSetChanged()
                    }

                    is TracksData.DataError -> {
                        showPlaceholder(R.drawable.empty_list_tracks, dataTracks.message)
                    }

                    is TracksData.NetworkError -> {
                        showError(dataTracks.message)
                    }
                }
            }
        }
        tracksInteractor.searchTracks(searchText, collection)
    }

    private fun initTrackListView() {
        trackListHistory = tracksHistoryInteractor.getTrackHistory()

        trackAdapter = TrackAdapter { track ->
            updateTrackListHistory(track)
            startAudioPlayer(track)
        }
        if (trackListHistory.isNotEmpty()) {
            showTrackHistory()
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewBinding.trackList.layoutManager = layoutManager
        viewBinding.trackList.adapter = trackAdapter
        viewBinding.trackListHistory.adapter = trackAdapter
    }

    private fun updateTrackListHistory(track: Track) {
        val existingTrackIndex = trackListHistory.indexOfFirst { it.trackId == track.trackId }
        if (existingTrackIndex != -1) {
            trackListHistory.removeAt(existingTrackIndex)
        }
        trackListHistory.add(0, track)
        if (trackListHistory.size > 10) {
            trackListHistory.removeAt(trackListHistory.size - 1)
        }
    }

    private fun showTrackHistory() {
        trackAdapter.setItems(trackListHistory)
        viewBinding.trackList.isVisible = false
        viewBinding.searchHistory.isVisible = true
    }

    private fun hideError() {
        viewBinding.updateButton.isVisible = false
        viewBinding.emptyImageView.isVisible = false
        viewBinding.errorText.isVisible = false
    }

    private fun showError(textError: String) {
        showPlaceholder(R.drawable.track_internet_error, textError)
        makeButtonRound()
        viewBinding.updateButton.isVisible = true
    }

    private fun makeButtonRound() {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE

        val cornerRadiusInPx = resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, resources.displayMetrics
        )
        viewBinding.updateButton.background = drawable
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun showPlaceholder(imageRes: Int, errorText: String) {
        viewBinding.trackList.isVisible = false

        viewBinding.emptyImageView.isVisible = true
        viewBinding.emptyImageView.setImageResource(imageRes)

        viewBinding.errorText.isVisible = true
        viewBinding.errorText.text = errorText
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewBinding.clearIcon.windowToken, 0)
    }

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val TRACK_DATA = "TRACK_DATA"

        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}