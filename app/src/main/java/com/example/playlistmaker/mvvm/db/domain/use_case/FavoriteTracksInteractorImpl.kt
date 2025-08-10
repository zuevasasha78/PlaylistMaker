package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.TracksRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteTracksInteractorImpl(
    private val tracksRepository: TracksRepository
) : FavoriteTracksInteractor {

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return tracksRepository.getTracks()
    }

    override suspend fun setFavoriteTrack(track: Track) {
        tracksRepository.setTracks(track)
    }

    override suspend fun getFavoriteTrackById(trackId: Long): Track? {
        return tracksRepository.getTrackById(trackId)
    }

    override suspend fun deleteFavoriteTrack(trackId: Long) {
        tracksRepository.deleteTracks(trackId)
    }
}
