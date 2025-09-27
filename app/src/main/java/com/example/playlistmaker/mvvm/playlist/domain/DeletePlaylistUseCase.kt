package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track

interface DeletePlaylistUseCase {

    suspend fun execute(playlistId: Long, tracksList: List<Track>?)
}
