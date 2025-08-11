package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.search.domain.models.Track

class RemoveTrackFromPlaylistUseCaseImpl(
    private val playlistRepository: PlaylistsRepository,
    private val tracksRepositoryDb: TracksRepositoryDb,
) : RemoveTrackFromPlaylistUseCase {

    override suspend fun execute(playlist: Playlist, track: Track) {
        val updatedPlaylist = playlist.copy(
            tracksList = playlist.tracksList - track.trackId,
            tracksAmount = if (playlist.tracksAmount > 0) playlist.tracksAmount - 1 else 0
        )
        playlistRepository.update(updatedPlaylist)

        playlistRepository.getPlaylistsLists().collect { playlistList ->
            val tracksList = playlistList.flatMap { it.tracksList }
            if (!track.isFavorite && !tracksList.contains(track.trackId)) {
                tracksRepositoryDb.deleteTracks(track.trackId)
            }
        }
    }
}
