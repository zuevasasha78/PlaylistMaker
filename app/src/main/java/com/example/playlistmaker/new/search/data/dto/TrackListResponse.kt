package com.example.playlistmaker.new.search.data.dto

import com.example.playlistmaker.new.audioplayer.data.dto.TrackDto

data class TrackListResponse(
    val resultCount: Int,
    val results: MutableList<TrackDto>?
) : Response()