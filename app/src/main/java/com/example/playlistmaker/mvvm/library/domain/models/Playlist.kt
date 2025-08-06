package com.example.playlistmaker.mvvm.library.domain.models

data class Playlist(
    val name: String,
    val description: String?,
    val coverImageUrl: String?,
    val tracksList: List<Long>?,
    val tracksAmount: Int,
)
