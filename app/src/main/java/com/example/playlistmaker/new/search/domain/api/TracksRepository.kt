package com.example.playlistmaker.new.search.domain.api

interface TracksRepository {
    fun searchTracks(expression: String): SearchState
}
