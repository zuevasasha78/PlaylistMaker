package com.example.playlistmaker.mvvm.search.domain.impl

import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor

class TrackConsumerImpl(private val onTracksReceived: (SearchState) -> Unit) :
    TracksInteractor.TrackConsumer {

    override fun consume(data: SearchState) {
        onTracksReceived(data)
    }
}