package com.example.playlistmaker

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.SearchActivity.Companion.TRACK_DATA
import com.example.playlistmaker.network.data.Track

class AudioPlayerActivity : AppCompatActivity() {

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_UPDATE_RATE = 300L
    }

    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private var duration = 0
    private lateinit var track: Track
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: ImageView
    private lateinit var stopOnTime: TextView
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audioplayer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val trackImage = findViewById<ImageView>(R.id.trackImage)
        val trackName = findViewById<TextView>(R.id.trackName)
        val artistName = findViewById<TextView>(R.id.artistName)
        val durationValue = findViewById<TextView>(R.id.durationValue)
        val albumText = findViewById<TextView>(R.id.albumText)
        val yearValue = findViewById<TextView>(R.id.yearValue)
        val genreName = findViewById<TextView>(R.id.genreName)
        val countryName = findViewById<TextView>(R.id.countryName)
        stopOnTime = findViewById(R.id.stopOnTime)
        playButton = findViewById(R.id.playButton)

        track = stringToObject(intent.getStringExtra(TRACK_DATA), Track::class.java)
        mediaPlayer = MediaPlayer()
        preparePlayer()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.audioplayer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        uploadImage(trackImage)
        trackName.text = track.trackName
        artistName.text = track.artistName
        durationValue.text = convertMsToData(track.trackTimeMillis, "mm:ss")
        if (!track.collectionName.isNullOrEmpty()) {
            albumText.text = track.collectionName
        } else {
            val albumLine = findViewById<Group>(R.id.albumLine)
            albumLine.isVisible = false
        }
        yearValue.text = convertStringToData(track.releaseDate, "yyyy")
        genreName.text = track.primaryGenreName
        countryName.text = track.country
        stopOnTime.text = durationFormat(duration)

        runnable = object : Runnable {
            override fun run() {
                duration = mediaPlayer.currentPosition
                stopOnTime.text = durationFormat(duration)
                handler.postDelayed(this, TIMER_UPDATE_RATE)
            }
        }

        playButton.setOnClickListener {
            if (it.isEnabled) {
                playbackControl()
            }
        }
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

    private fun uploadImage(trackImage: ImageView) {
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
            .into(trackImage)
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(runnable)
            playButton.setImageResource(R.drawable.play_button)
            duration = 0
            stopOnTime.text = durationFormat(duration)
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.pause_button)
        playerState = STATE_PLAYING
        handler.post(runnable)
    }

    private fun pausePlayer() {
        handler.removeCallbacks(runnable)
        duration = mediaPlayer.currentPosition
        stopOnTime.text = durationFormat(duration)
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.play_button)
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
