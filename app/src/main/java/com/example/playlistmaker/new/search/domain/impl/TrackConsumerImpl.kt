package com.example.playlistmaker.new.search.domain.impl

import com.example.playlistmaker.new.search.domain.use_case.TracksInteractor
import com.example.playlistmaker.new.search.domain.api.SearchState

class TrackConsumerImpl(private val onTracksReceived: (SearchState) -> Unit) :
    TracksInteractor.TrackConsumer {

    override fun consume(data: SearchState) {
        onTracksReceived(data)
    }
}