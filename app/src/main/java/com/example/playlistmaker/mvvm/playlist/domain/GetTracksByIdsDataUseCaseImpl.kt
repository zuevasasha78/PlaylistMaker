package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class GetTracksByIdsDataUseCaseImpl(
    private val tracksRepositoryDb: TracksRepositoryDb
) : GetTracksByIdsDataUseCase {

    override fun execute(tracksIds: List<Long>): Flow<List<Track>> {
        return tracksRepositoryDb.getTracksByIds(tracksIds)
    }
}
