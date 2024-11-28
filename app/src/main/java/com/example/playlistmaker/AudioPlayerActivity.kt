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

    private lateinit var track: Track
    private lateinit var mediaPlayer: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())

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
        val playButton = findViewById<ImageView>(R.id.playButton)
        val stopOnTime = findViewById<TextView>(R.id.stopOnTime)

        track = stringToObject(intent.getStringExtra(TRACK_DATA), Track::class.java)

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
        stopOnTime.text = durationFormat(0)

        //media player
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()

        val updateTimeRunnable = object : Runnable {
            override fun run() {
                if (mediaPlayer.isPlaying) {
                    val currentPosition = mediaPlayer.currentPosition
                    stopOnTime.text = durationFormat(currentPosition)
                    handler.postDelayed(this, 1_000)
                }
            }
        }

        playButton.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                playButton.setImageResource(R.drawable.pause_button)
                handler.post(updateTimeRunnable)
            } else {
                mediaPlayer.pause()
                playButton.setImageResource(R.drawable.play_button)
                handler.removeCallbacks(updateTimeRunnable)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
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
}
