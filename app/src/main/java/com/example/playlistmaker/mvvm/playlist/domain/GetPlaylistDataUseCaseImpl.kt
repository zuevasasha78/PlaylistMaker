package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.library.domain.models.Playlist

class GetPlaylistDataUseCaseImpl(
    private val playlistRepository: PlaylistsRepository
) : GetPlaylistDataUseCase {

    override suspend fun execute(playlistId: Long): Playlist {
        return playlistRepository.getPlaylistById(playlistId)
    }
}
