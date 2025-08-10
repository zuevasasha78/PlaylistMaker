package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTracksRepository {

    fun getTracks(): Flow<List<Track>>

    suspend fun setTracks(track: Track)

    suspend fun deleteTracks(trackId: Long)

    suspend fun getTrackById(trackId: Long): Track?
}
