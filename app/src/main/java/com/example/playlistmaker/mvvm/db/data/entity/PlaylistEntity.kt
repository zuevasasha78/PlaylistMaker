package com.example.playlistmaker.mvvm.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String?,
    val coverImageUrl: String?,
    val tracksList: String?,
    val tracksAmount: Int,
)
