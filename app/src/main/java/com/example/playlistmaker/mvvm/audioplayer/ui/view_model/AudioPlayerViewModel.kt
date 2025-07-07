package com.example.playlistmaker.mvvm.audioplayer.ui.view_model

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.TrackInteractor
import com.example.playlistmaker.mvvm.audioplayer.ui.model.PlayerState
import com.example.playlistmaker.mvvm.audioplayer.ui.model.PlayerState.Default
import com.example.playlistmaker.mvvm.audioplayer.ui.model.PlayerState.Paused
import com.example.playlistmaker.mvvm.audioplayer.ui.model.PlayerState.Playing
import com.example.playlistmaker.mvvm.audioplayer.ui.model.PlayerState.Prepared
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.utils.durationFormat
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioPlayerViewModel(
    private val trackInteractor: TrackInteractor,
    private val mediaPlayer: MediaPlayer,
) : ViewModel() {

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    private val playerState = MutableLiveData<PlayerState>(Default())
    private var timerJob: Job? = null

    companion object {
        private const val PROGRESS_DELAY = 300L
    }

    init {
        initPlayer()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    fun observePlayerState(): LiveData<PlayerState> = playerState

    fun onPlayButtonClicked() {
        when (playerState.value) {
            is Playing -> {
                pausePlayer()
            }

            is Prepared, is Paused -> {
                startPlayer()
            }

            else -> {}
        }
    }

    fun onPausePlayer() {
        pausePlayer()
    }

    private fun initPlayer() {
        val trackData = trackInteractor.getTrack()
        _track.value = trackData
        mediaPlayer.setDataSource(trackData.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState.postValue(Prepared())
        }
        mediaPlayer.setOnCompletionListener {
            playerState.postValue(Prepared())
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState.postValue(Playing(getCurrentPlayerPosition()))
        startTimer()
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        timerJob?.cancel()
        playerState.postValue(Paused(getCurrentPlayerPosition()))
    }

    private fun releasePlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
        playerState.value = Default()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (mediaPlayer.isPlaying) {
                delay(PROGRESS_DELAY)
                playerState.postValue(Playing(getCurrentPlayerPosition()))
            }
        }
    }

    private fun getCurrentPlayerPosition(): String {
        return durationFormat(mediaPlayer.currentPosition)
    }
}
