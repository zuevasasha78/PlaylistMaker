package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

class GetPlaylistListUseCaseImpl(
    private val playlistsRepository: PlaylistsRepository
) : GetPlaylistListUseCase {

    override fun execute(): Flow<List<Playlist>> {
        return playlistsRepository.getPlaylistsLists()
    }

}
