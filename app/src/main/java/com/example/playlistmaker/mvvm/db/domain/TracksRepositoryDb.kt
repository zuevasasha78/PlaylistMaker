package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepositoryDb {

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun setTracks(track: Track)

    suspend fun deleteTracks(trackId: Long)

    suspend fun getTrackById(trackId: Long): Track?
    fun getTracksByIds(trackIds: List<Long>): Flow<List<Track>>
}
