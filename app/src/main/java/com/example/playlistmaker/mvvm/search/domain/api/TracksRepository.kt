package com.example.playlistmaker.mvvm.search.domain.api

import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTracks(expression: String): Flow<SearchState>
}
