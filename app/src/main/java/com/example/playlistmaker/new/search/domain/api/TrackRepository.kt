package com.example.playlistmaker.new.search.domain.api

import com.example.playlistmaker.new.search.domain.models.Track

interface TrackRepository {

    fun getTrack(): Track
}
