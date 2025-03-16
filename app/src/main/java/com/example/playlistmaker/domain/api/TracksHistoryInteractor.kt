package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

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

