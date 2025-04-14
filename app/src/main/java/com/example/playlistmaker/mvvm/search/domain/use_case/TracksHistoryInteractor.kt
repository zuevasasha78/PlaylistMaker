package com.example.playlistmaker.mvvm.search.domain.use_case

import com.example.playlistmaker.mvvm.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TracksHistoryInteractor(private val repository: TracksHistoryRepository) {

    fun clearTrackHistory() {
        repository.clearTrackList()
    }

    fun saveTrackHistory(trackList: MutableList<Track>) {
        repository.updateTrackList(trackList)
    }

    fun getTrackHistory(): MutableList<Track> {
        return repository.getTracksList()
    }
}