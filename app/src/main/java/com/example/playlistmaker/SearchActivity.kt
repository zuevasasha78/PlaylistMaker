package com.example.playlistmaker

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.network.RetrofitClient.iTunesService
import com.example.playlistmaker.network.data.Track
import com.example.playlistmaker.network.data.TrackListResponse
import com.example.playlistmaker.trackview.TrackAdapter
import com.example.playlistmaker.trackview.TrackHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private var savedText: String? = null
    private var searchText: String? = null
    private val trackList = mutableListOf<Track>()
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackHistoryAdapter: TrackHistoryAdapter
    private lateinit var searchHistory: SearchHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_search)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        val recyclerView = findViewById<RecyclerView>(R.id.trackList)
        val recyclerViewHistoryTracks = findViewById<RecyclerView>(R.id.trackListHistory)
        val updateButtonView = findViewById<Button>(R.id.updateButton)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearHistory = findViewById<Button>(R.id.clearHistory)
        val searchHistoryView = findViewById<ConstraintLayout>(R.id.searchHistory)

        val app = applicationContext as App
        searchHistory = SearchHistory(app.sharedPrefs)
        trackHistoryAdapter = TrackHistoryAdapter(mutableListOf())

        toolbar.setNavigationOnClickListener {
            finish()
        }

        savedInstanceState?.let {
            val savedText = savedInstanceState.getString(SEARCH_TEXT)
            inputEditText.setText(savedText)
        }

        trackAdapter = TrackAdapter(trackList) { track ->
            val existingTrackIndex = trackHistoryAdapter.tracksHistory.indexOfFirst { it.trackId == track.trackId }
            if (existingTrackIndex != -1) {
                trackHistoryAdapter.tracksHistory.removeAt(existingTrackIndex)
            }
            trackHistoryAdapter.tracksHistory.add(0, track)
            if (trackHistoryAdapter.tracksHistory.size > 10) {
                trackHistoryAdapter.tracksHistory.removeAt(trackHistoryAdapter.tracksHistory.size - 1)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = trackAdapter

        clearButton.setOnClickListener { v ->
            inputEditText.setText("")
            setViewVisible(v, false)
            hideKeyboard(v)
            trackAdapter.trackList = mutableListOf()
            trackAdapter.notifyDataSetChanged()

            hideError(recyclerView)
            searchHistoryView.isVisible = true
            trackHistoryAdapter.notifyDataSetChanged()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setViewVisible(clearButton, !s.isNullOrEmpty())
                if (s.isNullOrEmpty()) {
                    searchHistoryView.isVisible = true
                    trackHistoryAdapter.notifyDataSetChanged()
                } else {
                    searchHistoryView.isVisible = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                savedText = s.toString()
            }
        }
        inputEditText.addTextChangedListener(textWatcher)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchText = textView.text.toString()
                executeSearchRequest(searchText, trackAdapter, recyclerView, updateButtonView)
                true
            }
            false
        }

        addSearchHistoryRecyclerView(inputEditText, recyclerView, recyclerViewHistoryTracks)

        updateButtonView.setOnClickListener { view ->
            executeSearchRequest(searchText, trackAdapter, recyclerView, view)
        }

        clearHistory.setOnClickListener {
            trackHistoryAdapter.tracksHistory = mutableListOf()
            searchHistory.clearTrackList()
            searchHistoryView.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        trackAdapter.trackList = searchHistory.getTrackList()
    }

    override fun onStop() {
        super.onStop()
        searchHistory.updateTrackList(trackHistoryAdapter.tracksHistory)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText?.let { outState.putString(SEARCH_TEXT, savedText) }
    }

    private fun addSearchHistoryRecyclerView(
        inputEditText: EditText,
        recyclerViewTracks: RecyclerView,
        recyclerViewHistoryTracks: RecyclerView
    ) {
        if (searchHistory.getTrackList().isNotEmpty()) {
            trackHistoryAdapter.tracksHistory = searchHistory.getTrackList()
        }

        recyclerViewHistoryTracks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewHistoryTracks.adapter = trackHistoryAdapter

        val searchHistoryView = findViewById<ConstraintLayout>(R.id.searchHistory)
        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus &&
                inputEditText.text.isNullOrEmpty()
            ) {
                if (trackHistoryAdapter.tracksHistory.isNotEmpty()) {
                    searchHistoryView.isVisible = true
                    trackHistoryAdapter.notifyDataSetChanged()
                    recyclerViewTracks.isVisible = false
                } else {
                    searchHistoryView.isVisible = false
                }
            } else {
                searchHistoryView.isVisible = false
            }
        }
    }

    private fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun setViewVisible(v: View, visible: Boolean) {
        v.isVisible = visible
    }

    private fun executeSearchRequest(
        searchText: String?,
        adapter: TrackAdapter,
        recyclerView: RecyclerView,
        updateButtonView: View
    ) {
        if (!searchText.isNullOrEmpty()) {
            iTunesService.search(searchText).enqueue(object : Callback<TrackListResponse> {
                override fun onResponse(call: Call<TrackListResponse>, response: Response<TrackListResponse>) {
                    if (response.isSuccessful) {
                        val trackList = response.body() ?: return
                        val tracks = trackList.results ?: return
                        if (tracks.isNotEmpty()) {
                            adapter.trackList = tracks
                            adapter.notifyDataSetChanged()

                            hideError(recyclerView)
                        } else {
                            val text = "Ничего не нашлось"
                            showPlaceholder(recyclerView, R.drawable.empty_list_tracks, text)
                        }
                    } else {
                        internetError(recyclerView, updateButtonView)
                    }
                }

                override fun onFailure(call: Call<TrackListResponse>, t: Throwable) {
                    internetError(recyclerView, updateButtonView)
                }
            })
        }
    }

    private fun hideError(recyclerView: RecyclerView) {
        val imageErrorView = findViewById<ImageView>(R.id.emptyImageView)
        val errorTextView = findViewById<TextView>(R.id.errorText)
        val updateButtonView = findViewById<Button>(R.id.updateButton)
        updateButtonView.isVisible = false
        imageErrorView.isVisible = false
        errorTextView.isVisible = false

        recyclerView.isVisible = true
    }

    private fun internetError(recyclerView: RecyclerView, button: View) {
        val text = "Проблемы со связью\n" +
            "\n" +
            "Загрузка не удалась. Проверьте подключение к интернету"
        showPlaceholder(recyclerView, R.drawable.track_internet_error, text)
        makeButtonRound(button)
        button.isVisible = true
    }

    private fun makeButtonRound(button: View) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE

        val cornerRadiusInPx = resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
        drawable.cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, resources.displayMetrics
        )
        button.background = drawable
    }

    private fun showPlaceholder(recyclerView: RecyclerView, imageRes: Int, errorText: String) {
        recyclerView.isVisible = false

        val imageErrorView = findViewById<ImageView>(R.id.emptyImageView)
        val errorTextView = findViewById<TextView>(R.id.errorText)

        imageErrorView.isVisible = true
        imageErrorView.setImageResource(imageRes)

        errorTextView.isVisible = true
        errorTextView.text = errorText
    }

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }
}
