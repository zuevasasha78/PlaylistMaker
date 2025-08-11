package com.example.playlistmaker.mvvm.playlist.ui.view_modal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.GetTracksByIdsDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.RemoveTrackFromPlaylistUseCase
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistId: Long,
    private val getPlaylistDataUseCase: GetPlaylistDataUseCase,
    private val getTracksByIdsDataUseCase: GetTracksByIdsDataUseCase,
    private val removeTrackFromPlaylistUseCase: RemoveTrackFromPlaylistUseCase,
) : ViewModel() {

    private val _playlistLiveData = MutableLiveData<Playlist>()
    val playlistLiveData: LiveData<Playlist> = _playlistLiveData
    private val _tracksListLiveData = MutableLiveData<List<Track>>()
    val tracksListLiveData: LiveData<List<Track>> = _tracksListLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = getPlaylistDataUseCase.execute(playlistId)
            _playlistLiveData.postValue(playlist)
            updateTrackInPlaylist(playlist)
        }
    }

    private suspend fun updateTrackInPlaylist(playlist: Playlist) {
        getTracksByIdsDataUseCase.execute(playlist.tracksList).collect { tracksList ->
            _tracksListLiveData.postValue(tracksList)
        }
    }

    fun removeTrackFromPlaylist(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = _playlistLiveData.value

            playlist?.let {
                removeTrackFromPlaylistUseCase.execute(playlist, track)
                val updatedPlaylist = getPlaylistDataUseCase.execute(playlistId)

                updateTrackInPlaylist(updatedPlaylist)
                _playlistLiveData.postValue(updatedPlaylist)
            }
        }
    }
}
