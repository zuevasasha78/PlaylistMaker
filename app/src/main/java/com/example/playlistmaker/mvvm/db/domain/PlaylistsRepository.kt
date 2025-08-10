package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {

    suspend fun setPlaylist(playlist: Playlist)

    fun getPlaylistsLists(): Flow<List<Playlist>>

    suspend fun update(playlist: Playlist)

    fun getPlaylistById(playlistId: Long): Playlist
}
