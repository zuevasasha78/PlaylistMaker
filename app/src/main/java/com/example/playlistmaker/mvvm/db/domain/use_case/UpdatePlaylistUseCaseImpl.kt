package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.library.domain.models.Playlist

class UpdatePlaylistUseCaseImpl(
    private val playlistsRepository: PlaylistsRepository
) : UpdatePlaylistUseCase {
    override suspend fun execute(playlist: Playlist) {
        playlistsRepository.update(playlist)
    }
}
