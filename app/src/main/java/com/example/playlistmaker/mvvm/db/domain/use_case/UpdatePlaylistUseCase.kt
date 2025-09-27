package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.library.domain.models.Playlist

interface UpdatePlaylistUseCase {

    suspend fun execute(playlist: Playlist)
}
