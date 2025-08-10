package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteTracksInteractorImpl(
    private val tracksRepositoryDb: TracksRepositoryDb
) : FavoriteTracksInteractor {

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return tracksRepositoryDb.getTracks()
    }

    override suspend fun setFavoriteTrack(track: Track) {
        tracksRepositoryDb.setTracks(track)
    }

    override suspend fun getFavoriteTrackById(trackId: Long): Track? {
        return tracksRepositoryDb.getTrackById(trackId)
    }

    override suspend fun deleteFavoriteTrack(trackId: Long) {
        tracksRepositoryDb.deleteTracks(trackId)
    }
}
