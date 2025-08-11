package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.search.domain.api.TrackRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track

class GetTrackUseCase(
    private val repository: TrackRepository,
    private val tracksRepositoryDb: TracksRepositoryDb,
) : GetTrackUseCaseImpl {

    override suspend fun execute(): Track {
        val track = repository.getTrack()
        tracksRepositoryDb.getTrackById(track.trackId)?.let { trackFromDb ->
            return track.copy(isFavorite = trackFromDb.isFavorite)
        }
        return track
    }
}
