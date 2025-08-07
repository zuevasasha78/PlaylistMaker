package com.example.playlistmaker.mvvm.library.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.db.domain.use_case.GetPlaylistListUseCase
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val getPlaylistListUseCase: GetPlaylistListUseCase,
) : ViewModel() {

    private val _playlistLiveData = MutableLiveData<List<Playlist>>()
    val playlistLiveData: LiveData<List<Playlist>> = _playlistLiveData

    fun updatePlaylistList() {
        viewModelScope.launch {
            getPlaylistListUseCase.execute().collect { playlist ->
                _playlistLiveData.postValue(playlist)
            }
        }
    }
}
