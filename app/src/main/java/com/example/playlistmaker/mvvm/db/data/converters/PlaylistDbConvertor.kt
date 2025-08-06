package com.example.playlistmaker.mvvm.db.data.converters

import com.example.playlistmaker.mvvm.db.data.entity.PlaylistEntity
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlaylistDbConvertor {

    val gson = Gson()
    fun map(playlist: Playlist): PlaylistEntity {
        return PlaylistEntity(
            name = playlist.name,
            description = playlist.description,
            coverImageUrl = playlist.coverImageUrl,
            tracksList = gson.toJson(playlist.tracksList),
            tracksAmount = playlist.tracksAmount,
        )
    }

    fun map(playlist: PlaylistEntity): Playlist {
        return Playlist(
            name = playlist.name,
            description = playlist.description,
            coverImageUrl = playlist.coverImageUrl,
            tracksList = tracksToListLong(playlist.tracksList),
            tracksAmount = playlist.tracksAmount,
        )
    }

    private fun tracksToListLong(value: String?): List<Long>? {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType)
    }
}
