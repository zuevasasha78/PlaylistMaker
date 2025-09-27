package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.search.domain.models.Track

class SaveTrackToPlaylistUseCaseImpl(
    val playlistsRepository: PlaylistsRepository,
    val tracksRepositoryDb: TracksRepositoryDb,
) : SaveTrackToPlaylistUseCase {

    override suspend fun execute(playlist: Playlist, track: Track) {
        playlistsRepository.update(playlist)
        tracksRepositoryDb.setTracks(track)
    }
}
