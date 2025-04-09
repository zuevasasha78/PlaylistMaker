package com.example.playlistmaker.new.search.domain.impl

import com.example.playlistmaker.new.search.domain.use_case.TracksInteractor
import com.example.playlistmaker.new.search.domain.api.TracksRepository
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TracksInteractor.TrackConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(expression))
        }
    }
}