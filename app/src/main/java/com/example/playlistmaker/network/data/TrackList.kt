package com.example.playlistmaker.network.data

data class TrackList(
    val resultCount: Int? = null,
    val results: List<Track>? = null
)

data class Track(
    val trackName: String, val artistName: String, val trackTime: String, val artworkUrl100: String
)
