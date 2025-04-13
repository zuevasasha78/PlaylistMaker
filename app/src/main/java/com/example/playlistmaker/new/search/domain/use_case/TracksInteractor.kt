package com.example.playlistmaker.new.search.domain.use_case

import com.example.playlistmaker.new.search.domain.api.SearchState

interface TracksInteractor {

    fun searchTracks(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(data: SearchState)
    }
}