package com.example.playlistmaker.mvvm.audioplayer.data

import com.example.playlistmaker.mvvm.search.domain.api.TrackRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TrackRepositoryImpl(private val track: Track) : TrackRepository {
    override fun getTrack(): Track {
        return this.track
    }
}