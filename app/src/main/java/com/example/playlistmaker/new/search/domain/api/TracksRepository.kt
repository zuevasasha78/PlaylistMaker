package com.example.playlistmaker.new.search.domain.api

import com.example.playlistmaker.new.search.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): TracksData<List<Track>>
}
