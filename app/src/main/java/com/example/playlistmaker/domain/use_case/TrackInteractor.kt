package com.example.playlistmaker.domain.use_case

import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.models.Track

class TrackInteractor(private val repository: TrackRepository) {

    fun getTrack(): Track {
        return repository.getTrack()
    }
}
