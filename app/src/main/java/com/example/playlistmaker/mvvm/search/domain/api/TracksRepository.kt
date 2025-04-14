package com.example.playlistmaker.mvvm.search.domain.api

interface TracksRepository {
    fun searchTracks(expression: String): SearchState
}
