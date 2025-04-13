package com.example.playlistmaker.new.search.ui.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.new.search.domain.api.SearchState
import com.example.playlistmaker.new.search.domain.impl.TrackConsumerImpl
import com.example.playlistmaker.new.search.domain.models.Track
import com.example.playlistmaker.new.search.domain.use_case.TracksHistoryInteractor
import com.example.playlistmaker.new.search.domain.use_case.TracksInteractor

class SearchViewModel(
    private val tracksInteractor: TracksInteractor,
    private val tracksHistoryInteractor: TracksHistoryInteractor
) : ViewModel() {

    private val handler = Handler(Looper.getMainLooper())
    private val _searchResultsLiveData = MutableLiveData<SearchState>()
    val searchResultsLiveData: LiveData<SearchState> = _searchResultsLiveData
    private val _trackHistoryLiveData = MutableLiveData<List<Track>>()
    val trackHistoryLiveData: LiveData<List<Track>> = _trackHistoryLiveData

    private var searchRunnable: Runnable? = null

    private val trackListHistory = mutableListOf<Track>()

    init {
        trackListHistory.addAll(tracksHistoryInteractor.getTrackHistory())
        _trackHistoryLiveData.value = trackListHistory
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        fun getViewModelFactory():
                ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val tracksInteractor = Creator.provideTracksInteractor()
                val provideTracksHistoryRepository = Creator.provideTracksHistoryRepository()
                val tracksHistoryInteractor =
                    Creator.provideTracksHistoryInteractor(provideTracksHistoryRepository)
                SearchViewModel(tracksInteractor, tracksHistoryInteractor)
            }
        }
    }

    fun onSearchTextChanged(query: String) {
        searchRunnable?.let { handler.removeCallbacks(it) }

        searchRunnable = Runnable { performSearch(query) }
        handler.postDelayed(searchRunnable!!, SEARCH_DEBOUNCE_DELAY)
    }

    private fun performSearch(query: String) {
        val collection = TrackConsumerImpl { dataTracks ->
            _searchResultsLiveData.postValue(dataTracks)
        }
        tracksInteractor.searchTracks(query, collection)
    }

    fun clearHistory() {
        trackListHistory.clear()
        tracksHistoryInteractor.clearTrackHistory()
        _trackHistoryLiveData.value = trackListHistory
    }

    fun updateTrackListHistory(track: Track) {
        val existingTrackIndex = trackListHistory.indexOfFirst { it.trackId == track.trackId }
        if (existingTrackIndex != -1) {
            trackListHistory.removeAt(existingTrackIndex)
        }
        trackListHistory.add(0, track)
        if (trackListHistory.size > 10) {
            trackListHistory.removeAt(trackListHistory.size - 1)
        }
        _trackHistoryLiveData.postValue(trackListHistory)
    }

    fun saveTrackHistory() {
        tracksHistoryInteractor.saveTrackHistory(trackListHistory)
    }
}