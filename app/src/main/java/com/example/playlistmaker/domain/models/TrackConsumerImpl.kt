package com.example.playlistmaker.domain.models

import com.example.playlistmaker.domain.api.TracksData
import com.example.playlistmaker.domain.use_case.TracksInteractor

class TrackConsumerImpl(private val onTracksReceived: (TracksData<List<Track>>) -> Unit) :
    TracksInteractor.TrackConsumer {

    override fun consume(data: TracksData<List<Track>>) {
        onTracksReceived(data)
    }
}
