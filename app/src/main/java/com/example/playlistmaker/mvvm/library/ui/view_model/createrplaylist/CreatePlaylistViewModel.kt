package com.example.playlistmaker.mvvm.library.ui.view_model.createrplaylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCase
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.launch

open class CreatePlaylistViewModel(
    private val createPlaylistUseCase: CreatePlaylistUseCase,
) : ViewModel() {

    fun createPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            createPlaylistUseCase.execute(playlist)
        }
    }
}
