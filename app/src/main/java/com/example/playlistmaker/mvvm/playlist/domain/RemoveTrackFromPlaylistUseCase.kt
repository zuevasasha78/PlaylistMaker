package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.search.domain.models.Track

interface RemoveTrackFromPlaylistUseCase {

    suspend fun execute(playlist: Playlist, track: Track)
}
