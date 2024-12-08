package com.example.playlistmaker.network.data

data class TrackListResponse(
    val resultCount: Int? = null,
    val results: MutableList<Track>? = null
)

data class Track(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int?,
    val artworkUrl100: String,
    val collectionName: String?,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
)
