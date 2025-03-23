package com.example.playlistmaker.data.dto

data class TrackListResponse(
    val resultCount: Int,
    val results: MutableList<TrackDto>?
) : Response()

