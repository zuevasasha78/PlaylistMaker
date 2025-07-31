package com.example.playlistmaker.mvvm.library.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.db.domain.use_case.FavoriteTracksInteractor
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.launch

class LikedTracksViewModel(
    private val favoriteTracksInteractor: FavoriteTracksInteractor,
) : ViewModel() {

    private val _favoriteTrackListLiveData = MutableLiveData<List<Track>>()
    val favoriteTrackListLiveData: LiveData<List<Track>> = _favoriteTrackListLiveData

    fun updateFavoriteTrackList() {
        viewModelScope.launch {
            favoriteTracksInteractor.getFavoriteTracks().collect { tracks ->
                _favoriteTrackListLiveData.postValue(tracks)
            }
        }
    }
}
