package com.example.playlistmaker.mvvm.audioplayer.ui.view_model

import android.media.MediaPlayer
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.TrackInteractor
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.utils.durationFormat

class AudioPlayerViewModel(
    private val trackInteractor: TrackInteractor,
    private val mediaPlayer: MediaPlayer,
    private val handler: Handler
) : ViewModel() {

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    private val _playerState = MutableLiveData<Int>()
    val playerState: LiveData<Int> get() = _playerState

    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String> get() = _currentTime

    private var duration = 0
    private val updateRunnable = object : Runnable {
        override fun run() {
            duration = mediaPlayer.currentPosition
            _currentTime.postValue(durationFormat(duration))
            handler.postDelayed(this, TIMER_UPDATE_RATE)
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_UPDATE_RATE = 300L
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(updateRunnable)
        mediaPlayer.release()
    }

    fun initPlayer() {
        _playerState.value = STATE_DEFAULT
        val trackData = trackInteractor.getTrack()
        _track.value = trackData
        mediaPlayer.setDataSource(trackData.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            _playerState.postValue(STATE_PREPARED)
        }

        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(updateRunnable)
            _playerState.postValue(STATE_PREPARED)
            duration = 0
            _currentTime.postValue(durationFormat(duration))
        }
    }

    fun playbackControl() {
        when (_playerState.value) {
            STATE_PLAYING -> pausePlayer()
            STATE_PREPARED, STATE_PAUSED -> startPlayer()
        }
    }

    fun pauseOnLifecycle() {
        if (mediaPlayer.isPlaying) {
            pausePlayer()
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        _playerState.postValue(STATE_PLAYING)
        handler.post(updateRunnable)
    }

    private fun pausePlayer() {
        handler.removeCallbacks(updateRunnable)
        duration = mediaPlayer.currentPosition
        mediaPlayer.pause()
        _playerState.postValue(STATE_PAUSED)
        _currentTime.postValue(durationFormat(duration))
    }
}
