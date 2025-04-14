package com.example.playlistmaker.mvvm.search.domain.api

import com.example.playlistmaker.mvvm.search.domain.models.Track

interface TracksHistoryRepository {
    fun clearTrackList()
    fun updateTrackList(trackListHistory: MutableList<Track>)
    fun getTracksList(): MutableList<Track>
}
