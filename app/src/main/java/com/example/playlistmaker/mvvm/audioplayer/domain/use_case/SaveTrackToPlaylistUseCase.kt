package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.library.domain.models.Playlist

interface SaveTrackToPlaylistUseCase {

    suspend fun execute(playlist: Playlist)
}
