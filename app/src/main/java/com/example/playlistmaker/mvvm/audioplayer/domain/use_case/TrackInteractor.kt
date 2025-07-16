package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.search.domain.api.TrackRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TrackInteractor(private val repository: TrackRepository) {

    fun getTrack(): Track {
        return repository.getTrack()
    }
}
