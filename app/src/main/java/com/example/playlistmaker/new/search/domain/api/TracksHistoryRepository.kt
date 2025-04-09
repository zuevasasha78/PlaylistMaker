package com.example.playlistmaker.new.search.domain.api

import com.example.playlistmaker.new.search.domain.models.Track

interface TracksHistoryRepository {
    fun clearTrackList()
    fun updateTrackList(trackListHistory: MutableList<Track>)
    fun getTracksList(): MutableList<Track>
}
