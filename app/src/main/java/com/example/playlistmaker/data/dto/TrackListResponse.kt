package com.example.playlistmaker.data.dto

data class TrackListResponse(
    val resultCount: Int? = null,
    val results: MutableList<TrackDto>? = null
) : Response()

