package com.example.playlistmaker.data.dto

import com.example.playlistmaker.domain.models.Track

data class TrackDto(
    val resultCount: Int? = null,
    val results: MutableList<Track>? = null
)

