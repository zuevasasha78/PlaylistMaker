package com.example.playlistmaker.domain.use_case

import com.example.playlistmaker.domain.api.TracksData
import com.example.playlistmaker.domain.models.Track

interface TracksInteractor {

    fun searchTracks(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(data: TracksData<List<Track>>)
    }
}
