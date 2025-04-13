package com.example.playlistmaker.mvvm.search.domain.api

import com.example.playlistmaker.mvvm.search.domain.models.Track

interface TrackRepository {

    fun getTrack(): Track
}
