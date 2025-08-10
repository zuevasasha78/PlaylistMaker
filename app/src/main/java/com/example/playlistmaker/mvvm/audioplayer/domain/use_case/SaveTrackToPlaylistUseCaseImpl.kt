package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.library.domain.models.Playlist

class SaveTrackToPlaylistUseCaseImpl(
    val playlistsRepository: PlaylistsRepository
) : SaveTrackToPlaylistUseCase {

    override suspend fun execute(playlist: Playlist) {
        playlistsRepository.update(playlist)
    }
}
