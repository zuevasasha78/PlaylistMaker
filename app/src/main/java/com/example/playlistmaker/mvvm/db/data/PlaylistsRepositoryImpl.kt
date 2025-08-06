package com.example.playlistmaker.mvvm.db.data

import com.example.playlistmaker.mvvm.db.data.converters.PlaylistDbConvertor
import com.example.playlistmaker.mvvm.db.data.dao.PlaylistDao
import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistsRepositoryImpl(
    private val playlistDto: PlaylistDao,
    private val playlistDbConvertor: PlaylistDbConvertor,
) : PlaylistsRepository {

    override suspend fun setPlaylist(playlist: Playlist) {
        playlistDto.insertPlaylist(playlistDbConvertor.map(playlist))
    }

    override fun getPlaylistsLists(): Flow<List<Playlist>> = flow {
        val playlistEntityList = playlistDto.getPlaylists()
        val playlistList = playlistEntityList.map { playlist -> playlistDbConvertor.map(playlist) }
        emit(playlistList)
    }
}
