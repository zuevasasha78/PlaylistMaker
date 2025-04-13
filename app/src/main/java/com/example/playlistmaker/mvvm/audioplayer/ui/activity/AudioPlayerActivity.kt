package com.example.playlistmaker.mvvm.audioplayer.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import com.example.playlistmaker.mvvm.audioplayer.ui.view_model.AudioPlayerViewModel
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.stringToObject

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAudioplayerBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var viewModel: AudioPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityAudioplayerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        mediaPlayer = MediaPlayer()
        val track = stringToObject(intent.getStringExtra(TRACK_DATA), Track::class.java)
        viewModel = ViewModelProvider(
            this,
            AudioPlayerViewModel.getViewModelFactory(track)
        )[AudioPlayerViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.audioplayer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        setupListeners()
        viewModel.initPlayer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseOnLifecycle()
    }

    private fun setupListeners() {
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
        viewBinding.playButton.setOnClickListener {
            viewModel.playbackControl()
        }
    }

    private fun initView() {
        viewModel.track.observe(this) { track ->
            with(viewBinding) {
                trackName.text = track.trackName
                artistName.text = track.artistName
                durationValue.text = track.trackTimeMillis
                yearValue.text = track.releaseDate
                genreName.text = track.primaryGenreName
                countryName.text = track.country

                if (!track.collectionName.isNullOrEmpty()) {
                    albumText.text = track.collectionName
                } else {
                    albumLine.isVisible = false
                }

                uploadImage(track.artworkUrl100)
            }
        }

        viewModel.playerState.observe(this) { state ->
            viewBinding.playButton.setImageResource(
                if (state == 2) R.drawable.pause_button else R.drawable.play_button
            )
            viewBinding.playButton.isEnabled = (state == 1 || state == 3)
        }

        viewModel.currentTime.observe(this) { time ->
            viewBinding.stopOnTime.text = time
        }
    }

    private fun uploadImage(artworkUrl100: String) {
        val roundValue = 8
        val url = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
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

    companion object {
        const val TRACK_DATA = "TRACK_DATA"
    }
}