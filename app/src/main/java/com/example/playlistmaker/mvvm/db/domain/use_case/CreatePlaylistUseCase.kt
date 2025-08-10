package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.library.domain.models.Playlist

interface CreatePlaylistUseCase {

    suspend fun execute(playlist: Playlist)
}
