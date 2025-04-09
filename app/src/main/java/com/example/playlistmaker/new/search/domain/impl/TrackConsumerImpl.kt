package com.example.playlistmaker.new.search.domain.impl

import com.example.playlistmaker.new.search.domain.use_case.TracksInteractor
import com.example.playlistmaker.new.search.domain.api.TracksData
import com.example.playlistmaker.new.search.domain.models.Track

class TrackConsumerImpl(private val onTracksReceived: (TracksData<List<Track>>) -> Unit) :
    TracksInteractor.TrackConsumer {

    override fun consume(data: TracksData<List<Track>>) {
        onTracksReceived(data)
    }
}