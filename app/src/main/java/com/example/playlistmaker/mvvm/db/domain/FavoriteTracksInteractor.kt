package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksInteractor {

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun setFavoriteTrack(track: Track)
}
