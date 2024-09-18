package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
            albumText.isVisible = false
        }
        yearValue.text = convertStringToData(track.releaseDate, "yyyy")
        genreName.text = track.primaryGenreName
        countryName.text = track.country
    }

    override fun onResume() {
        super.onResume()
        // todo для фонового режима???
    }

    override fun onStop() {
        super.onStop()
        // todo для фонового режима???
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
