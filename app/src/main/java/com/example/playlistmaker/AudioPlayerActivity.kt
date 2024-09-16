package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        track = stringToTrackList(intent.getStringExtra(TRACK_DATA))!!

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.audioplayer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        uploadImage(trackImage)
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
        Glide.with(this)
            .load(track.artworkUrl100)
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
