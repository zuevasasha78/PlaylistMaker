package com.example.playlistmaker.mvvm.playlist.ui.view_modal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistId: Long,
    private val getPlaylistDataUseCase: GetPlaylistDataUseCase
) : ViewModel() {

    private val _playlistLiveData = MutableLiveData<Playlist>()
    val playlistLiveData: LiveData<Playlist> = _playlistLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = getPlaylistDataUseCase.execute(playlistId)
            _playlistLiveData.postValue(playlist)
        }
    }
}
