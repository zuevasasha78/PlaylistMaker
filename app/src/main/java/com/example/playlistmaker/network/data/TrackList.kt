package com.example.playlistmaker.network.data

data class TrackList(
    val resultCount: Int? = null,
    val results: List<Track>? = null
)

data class Track(
    val trackName: String, val artistName: String, val trackTimeMillis: Int?, val artworkUrl100: String
)
