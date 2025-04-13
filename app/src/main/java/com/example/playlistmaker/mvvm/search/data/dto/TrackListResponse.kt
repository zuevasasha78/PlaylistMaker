package com.example.playlistmaker.mvvm.search.data.dto

import com.example.playlistmaker.mvvm.audioplayer.data.dto.TrackDto

data class TrackListResponse(
    val resultCount: Int,
    val results: MutableList<TrackDto>?
) : Response()