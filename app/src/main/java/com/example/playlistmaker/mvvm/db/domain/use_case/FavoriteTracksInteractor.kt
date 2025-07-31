package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksInteractor {

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun getFavoriteTrackById(trackId: Long): Track?

    suspend fun setFavoriteTrack(track: Track)

    suspend fun deleteFavoriteTrack(trackId: Long)
}
