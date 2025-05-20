package com.example.playlistmaker.mvvm.library.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistViewModel : ViewModel() {

    private val _playlistLiveData = MutableLiveData<List<Any>>()
    val playlistLiveData: LiveData<List<Any>> = _playlistLiveData

    init {
        _playlistLiveData.value = listOf()
    }
}
