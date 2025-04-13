package com.example.playlistmaker.mvvm.search.domain.api

import com.example.playlistmaker.mvvm.search.domain.models.Track

sealed interface SearchState {
    data class Success(val data: List<Track>) : SearchState
    data class NetworkError(val message: String) : SearchState
    data class DataError(val message: String) : SearchState
}
