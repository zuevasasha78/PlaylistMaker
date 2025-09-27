package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.search.domain.models.Track

interface SaveTrackToPlaylistUseCase {

    suspend fun execute(playlist: Playlist, track: Track)
}
