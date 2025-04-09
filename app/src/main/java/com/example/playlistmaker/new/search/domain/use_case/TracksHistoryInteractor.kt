package com.example.playlistmaker.new.search.domain.use_case

import com.example.playlistmaker.new.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.new.search.domain.models.Track

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