package com.example.playlistmaker.domain.api

import android.os.Bundle
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.stringToObject

class GetTrackInteractor {

    fun execute(bundle: Bundle?): Track {
        val trackDataJson = bundle?.getString(TRACK_DATA)
        return stringToObject(trackDataJson, Track::class.java)
    }

    companion object {
        const val TRACK_DATA = "TRACK_DATA"
    }
}
