package com.example.playlistmaker.mvvm.library.ui.view_model.createrplaylist

import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCase
import com.example.playlistmaker.mvvm.db.domain.use_case.UpdatePlaylistUseCase
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditableCreatePlaylistViewModel(
    private val playlist: Playlist,
    private val updatePlaylistUseCase: UpdatePlaylistUseCase,
    createPlaylistUseCase: CreatePlaylistUseCase,
) : CreatePlaylistViewModel(createPlaylistUseCase) {

    fun updatePlaylist(updatedPlaylist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updatedPlaylist != playlist) {
                updatePlaylistUseCase.execute(updatedPlaylist)
            }
        }
    }
}
