package com.example.playlistmaker.mvvm.search.domain.impl

import com.example.playlistmaker.mvvm.search.domain.api.TracksRepository
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    override fun searchTracks(expression: String, consumer: TracksInteractor.TrackConsumer) {
        consumer.consume(repository.searchTracks(expression))
    }
}
