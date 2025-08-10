package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.library.domain.models.Playlist

interface GetPlaylistDataUseCase {

    suspend fun execute(playlistId: Long): Playlist
}
