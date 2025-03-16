package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface TracksHistoryRepository {
    fun clearTrackList()
    fun updateTrackList(trackListHistory: MutableList<Track>)
    fun getTracksList(): MutableList<Track>
}
