package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksRepository {

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun setFavoriteTracks(track: Track)
}
