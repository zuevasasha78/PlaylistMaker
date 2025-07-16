package com.example.playlistmaker.mvvm.db.domain

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
}
