package com.example.playlistmaker.mvvm.db.data.converters

import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TrackDbConvertor {

    fun map(track: Track): TrackEntity {
        return TrackEntity(
            trackName = track.trackName,
            artworkUrl100 = track.artworkUrl100,
            artistName = track.artistName,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            trackDuration = track.trackTimeMillis,
            previewUrl = track.previewUrl,
        )
    }

    fun map(track: TrackEntity): Track {
        return Track(
            trackId = track.id.toInt(),
            trackName = track.trackName,
            artworkUrl100 = track.artworkUrl100,
            artistName = track.artistName,
            trackTimeMillis = track.trackDuration,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,

            )
    }
}
