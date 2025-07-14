package com.example.playlistmaker.mvvm.search.domain.use_case

import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import kotlinx.coroutines.flow.Flow

interface TracksInteractor {

    fun searchTracks(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(data: Flow<SearchState>)
    }
}
