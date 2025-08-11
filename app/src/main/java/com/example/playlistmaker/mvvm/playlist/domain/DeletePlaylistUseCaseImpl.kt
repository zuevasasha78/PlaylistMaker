package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.search.domain.models.Track

class DeletePlaylistUseCaseImpl(
    private val playlistRepository: PlaylistsRepository,
    private val tracksRepositoryDb: TracksRepositoryDb,
) : DeletePlaylistUseCase {

    override suspend fun execute(playlistId: Long, tracksList: List<Track>?) {
        playlistRepository.deletePlaylist(playlistId)

        tracksList?.let { tracks ->
            playlistRepository.getPlaylistsLists().collect { playlistList ->
                val allTracksSet = playlistList.flatMap { it.tracksList }.toSet()
                tracksList.filter { it.trackId !in allTracksSet && !it.isFavorite }.forEach { track ->
                    tracksRepositoryDb.deleteTracks(track.trackId)
                }
            }
        }
    }
}
