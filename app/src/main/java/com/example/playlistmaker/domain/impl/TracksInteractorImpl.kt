package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.TracksInteractor
import com.example.playlistmaker.domain.api.TracksRepository
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TracksInteractor.TrackConsumer) {
        // TODO: десь можно было бы пересортировать список фильмов,
        //  отфильтровать, убрав ненужные результаты поиска,
        //  но в нашем примере этого не требуется. Поэтому сразу передаём полученный результат в мето
        //val t = Thread {
        //    consumer.consume(repository.searchMovies(expression))
        //}
        //t.start()
        executor.execute {
            consumer.consume(repository.searchTracks(expression))
        }
    }
}
