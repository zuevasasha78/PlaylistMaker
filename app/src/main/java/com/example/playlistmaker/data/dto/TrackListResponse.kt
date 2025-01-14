package com.example.playlistmaker.data.dto

data class TrackListResponse(
    //todo убрать ? null
    val resultCount: Int? = null,
    val results: MutableList<TrackDto>? = null
) : Response()

