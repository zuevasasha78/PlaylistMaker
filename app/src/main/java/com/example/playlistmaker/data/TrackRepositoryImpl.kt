package com.example.playlistmaker.data

import android.content.Intent
import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.stringToObject

class TrackRepositoryImpl(private val intent: Intent) : TrackRepository {
    override fun getTrack(): Track {
        return stringToObject(intent.getStringExtra(TRACK_DATA), Track::class.java)
    }

    companion object {
        const val TRACK_DATA = "TRACK_DATA"
    }
}
