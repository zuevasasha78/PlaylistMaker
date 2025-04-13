package com.example.playlistmaker.new.audioplayer.domain.use_case

import com.example.playlistmaker.new.search.domain.api.TrackRepository
import com.example.playlistmaker.new.search.domain.models.Track

class TrackInteractor(private val repository: TrackRepository) {

    fun getTrack(): Track {
        return repository.getTrack()
    }
}