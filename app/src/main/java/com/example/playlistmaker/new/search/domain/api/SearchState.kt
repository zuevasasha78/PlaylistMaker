package com.example.playlistmaker.new.search.domain.api

import com.example.playlistmaker.new.search.domain.models.Track

sealed interface SearchState {
    data class Success(val data: List<Track>) : SearchState
    data class NetworkError(val message: String) : SearchState
    data class DataError(val message: String) : SearchState
}
