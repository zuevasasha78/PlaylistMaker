package com.example.playlistmaker.mvvm.db.domain.use_case

import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface GetPlaylistListUseCase {

    fun execute(): Flow<List<Playlist>>
}
