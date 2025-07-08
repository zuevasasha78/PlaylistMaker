package com.example.playlistmaker.mvvm.search.domain.impl

import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor
import kotlinx.coroutines.flow.Flow

class TrackConsumerImpl(private val onTracksReceived: (Flow<SearchState>) -> Unit) :
    TracksInteractor.TrackConsumer {

    override fun consume(data: Flow<SearchState>) {
        onTracksReceived(data)
    }
}
