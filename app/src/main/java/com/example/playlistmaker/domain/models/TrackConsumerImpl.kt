package com.example.playlistmaker.domain.models

import com.example.playlistmaker.domain.api.TracksInteractor

class TrackConsumerImpl(private val onTracksReceived: (List<Track>) -> Unit) : TracksInteractor.TrackConsumer {
    private var list: List<Track> = emptyList()

    override fun consume(foundTracks: List<Track>) {
        this.list = foundTracks
        onTracksReceived(list)
    }
}
