package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.library.domain.models.Playlist

class CreatePlaylistUseCaseImpl(
    private val playlistsRepository: PlaylistsRepository
) : CreatePlaylistUseCase {

    override suspend fun execute(playlist: Playlist) {
        playlistsRepository.setPlaylist(playlist)
    }
}
