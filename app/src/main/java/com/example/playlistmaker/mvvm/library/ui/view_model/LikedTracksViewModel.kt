package com.example.playlistmaker.mvvm.library.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.mvvm.search.domain.models.Track

class LikedTracksViewModel : ViewModel() {

    private val _likedTracksLiveData = MutableLiveData<List<Track>>()
    val likedTracksLiveData: LiveData<List<Track>> = _likedTracksLiveData

    init {
        _likedTracksLiveData.value = listOf()
    }
}
