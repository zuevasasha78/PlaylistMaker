package com.example.playlistmaker.mvvm.search.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import com.example.playlistmaker.mvvm.search.domain.impl.TrackConsumerImpl
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksHistoryInteractor
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksInteractor: TracksInteractor,
    private val tracksHistoryInteractor: TracksHistoryInteractor
) : ViewModel() {

    private val _searchResultsLiveData = MutableLiveData<SearchState>()
    val searchResultsLiveData: LiveData<SearchState> = _searchResultsLiveData

    private val trackListHistory = mutableListOf<Track>()
    private var searchJob: Job? = null

    init {
        trackListHistory.addAll(tracksHistoryInteractor.getTrackHistory())
        _searchResultsLiveData.value = SearchState.History(trackListHistory)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    override fun onCleared() {
        super.onCleared()
        saveTrackHistory()
    }

    fun onSearchTextChanged(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        val collection = TrackConsumerImpl { dataTracksFlow ->
            viewModelScope.launch {
                dataTracksFlow.collect { searchState ->
                    _searchResultsLiveData.postValue(searchState)
                }
            }
        }
        tracksInteractor.searchTracks(query, collection)
    }

    fun clearHistory() {
        trackListHistory.clear()
        tracksHistoryInteractor.clearTrackHistory()
        _searchResultsLiveData.value = SearchState.History(trackListHistory)
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
        _searchResultsLiveData.postValue(SearchState.History(trackListHistory))
    }

    fun saveTrackHistory() {
        tracksHistoryInteractor.saveTrackHistory(trackListHistory)
    }
}
