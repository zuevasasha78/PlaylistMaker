package com.example.playlistmaker.mvvm.search.domain.impl

import com.example.playlistmaker.mvvm.search.domain.api.TracksRepository
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TracksInteractor.TrackConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(expression))
        }
    }
}