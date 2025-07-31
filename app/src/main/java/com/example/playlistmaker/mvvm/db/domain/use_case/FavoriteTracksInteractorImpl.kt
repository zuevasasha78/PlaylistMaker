package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.FavoriteTracksRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteTracksInteractorImpl(
    private val favoriteTracksRepository: FavoriteTracksRepository
) : FavoriteTracksInteractor {

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return favoriteTracksRepository.getFavoriteTracks()
    }

    override suspend fun setFavoriteTrack(track: Track) {
        favoriteTracksRepository.setFavoriteTracks(track)
    }

    override suspend fun getFavoriteTrackById(trackId: Long): Track? {
        return favoriteTracksRepository.getFavoriteTrackById(trackId)
    }

    override suspend fun deleteFavoriteTrack(trackId: Long) {
        favoriteTracksRepository.deleteFavoriteTracks(trackId)
    }
}
