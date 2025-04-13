package com.example.playlistmaker.new.audioplayer.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import com.example.playlistmaker.durationFormat
import com.example.playlistmaker.new.audioplayer.domain.use_case.TrackInteractor
import com.example.playlistmaker.new.search.domain.models.Track

class AudioPlayerActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_UPDATE_RATE = 300L
    }

    private lateinit var trackInteractor: TrackInteractor
    private lateinit var viewBinding: ActivityAudioplayerBinding
    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private var duration = 0
    private lateinit var track: Track
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityAudioplayerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        createInteractor()

        track = trackInteractor.getTrack()

        mediaPlayer = MediaPlayer()
        preparePlayer()

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.audioplayer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }

        uploadImage()
        viewBinding.trackName.text = track.trackName
        viewBinding.artistName.text = track.artistName
        viewBinding.durationValue.text = track.trackTimeMillis
        if (!track.collectionName.isNullOrEmpty()) {
            viewBinding.albumText.text = track.collectionName
        } else {
            viewBinding.albumLine.isVisible = false
        }
        viewBinding.yearValue.text = track.releaseDate
        viewBinding.genreName.text = track.primaryGenreName
        viewBinding.countryName.text = track.country
        viewBinding.stopOnTime.text = durationFormat(duration)

        runnable = object : Runnable {
            override fun run() {
                duration = mediaPlayer.currentPosition
                viewBinding.stopOnTime.text = durationFormat(duration)
                handler.postDelayed(this, TIMER_UPDATE_RATE)
            }
        }

        viewBinding.playButton.setOnClickListener {
            if (it.isEnabled) {
                playbackControl()
            }
        }
    }

    private fun createInteractor() {
        val providerTrackInteractor = Creator.provideTrackRepository(intent)
        trackInteractor = Creator.providerTrackInteractor(providerTrackInteractor)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        mediaPlayer.release()
    }

    private fun uploadImage() {
        val roundValue = 8
        val url = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .transform(
                FitCenter(),
                RoundedCorners(
                    roundValue * (resources.displayMetrics.density).toInt()
                )
            )
            .into(viewBinding.trackImage)
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            viewBinding.playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(runnable)
            viewBinding.playButton.setImageResource(R.drawable.play_button)
            duration = 0
            viewBinding.stopOnTime.text = durationFormat(duration)
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        viewBinding.playButton.setImageResource(R.drawable.pause_button)
        playerState = STATE_PLAYING
        handler.post(runnable)
    }

    private fun pausePlayer() {
        handler.removeCallbacks(runnable)
        duration = mediaPlayer.currentPosition
        viewBinding.stopOnTime.text = durationFormat(duration)
        mediaPlayer.pause()
        viewBinding.playButton.setImageResource(R.drawable.play_button)
        playerState = STATE_PAUSED
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
}