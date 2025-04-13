package com.example.playlistmaker.mvvm.search.ui.activity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.mvvm.audioplayer.ui.activity.AudioPlayerActivity
import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.mvvm.search.ui.view_model.SearchViewModel
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackHistoryAdapter: TrackAdapter

    private var savedText: String? = null
    private var isClickAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.search) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initTrackListView()
        observeViewModel()
        setupListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText?.let { outState.putString(SEARCH_TEXT, savedText) }
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

    private fun initTrackListView() {
        trackAdapter = TrackAdapter { track ->
            viewModel.updateTrackListHistory(track)
            startAudioPlayer(track)
        }
        trackHistoryAdapter = TrackAdapter { track ->
            startAudioPlayer(track)
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewBinding.trackList.layoutManager = layoutManager
        viewBinding.trackList.adapter = trackAdapter
        viewBinding.trackListHistory.adapter = trackHistoryAdapter
    }

    private fun observeViewModel() {
        viewModel.searchResultsLiveData.observe(this) { result ->
            when (result) {
                is SearchState.Success -> {
                    hideLoading()
                    trackAdapter.setItems(result.data)
                    viewBinding.trackList.isVisible = true
                }

                is SearchState.DataError -> {
                    showPlaceholder(R.drawable.empty_list_tracks, result.message)
                }

                is SearchState.NetworkError -> {
                    showError(result.message)
                }

                is SearchState.History -> {
                    if (result.history.isNotEmpty()) {
                        showTrackHistory(result.history)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        viewBinding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    val text = s.toString()
                    viewBinding.clearIcon.isVisible = true
                    showLoading()
                    viewModel.onSearchTextChanged(text)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                savedText = s.toString()
            }
        })

        viewBinding.clearIcon.setOnClickListener { v ->
            viewBinding.inputEditText.text.clear()
            viewBinding.trackList.isVisible = false
            v.isVisible = false
            hideKeyboard()
            hideError()
        }
        viewBinding.updateButton.setOnClickListener {
            showLoading()
            savedText?.let { viewModel.onSearchTextChanged(it) }
        }
        viewBinding.clearHistory.setOnClickListener {
            viewModel.clearHistory()
            viewBinding.searchHistory.isVisible = false
        }
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun hideLoading() {
        viewBinding.progressBar.isVisible = false
        viewBinding.searchHistory.isVisible = false
    }

    private fun showTrackHistory(trackHistory: List<Track>) {
        trackHistoryAdapter.setItems(trackHistory)
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

        val cornerRadiusInPx =
            resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, resources.displayMetrics
        )
        viewBinding.updateButton.background = drawable
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewBinding.root.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
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

        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}