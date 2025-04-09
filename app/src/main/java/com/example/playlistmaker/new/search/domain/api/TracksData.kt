package com.example.playlistmaker.new.search.domain.api

sealed interface TracksData<T> {
    data class Success<T>(val data: T) : TracksData<T>
    data class NetworkError<T>(val message: String) : TracksData<T>
    data class DataError<T>(val message: String) : TracksData<T>
}
