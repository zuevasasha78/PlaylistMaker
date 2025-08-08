package com.example.playlistmaker.mvvm.library.domain.models

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String?,
    val coverImageUrl: String?,
    val tracksList: List<Long>?,
    val tracksAmount: Int,
)
