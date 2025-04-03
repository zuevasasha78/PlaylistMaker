package com.example.playlistmaker.new.sharing.domain.model

data class EmailData(
    val email: Array<String>,
    val subject: String,
    val text: String,
)
