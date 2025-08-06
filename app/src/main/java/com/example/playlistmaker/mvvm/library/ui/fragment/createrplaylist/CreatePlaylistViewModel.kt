package com.example.playlistmaker.mvvm.library.ui.fragment.createrplaylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCase
import com.example.playlistmaker.mvvm.library.ui.models.Playlist
import kotlinx.coroutines.launch
import com.example.playlistmaker.mvvm.library.domain.models.Playlist as domainPlaylist

class CreatePlaylistViewModel(
    private val createPlaylistUseCase: CreatePlaylistUseCase,
) : ViewModel() {

    fun createPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            val domainPlaylist = domainPlaylist(
                name = playlist.name,
                description = playlist.description,
                coverImageUrl = playlist.coverImageUrl,
                tracksList = null,
                tracksAmount = 0,
            )
            createPlaylistUseCase.execute(domainPlaylist)
        }
    }
}
