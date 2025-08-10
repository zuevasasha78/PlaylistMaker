package com.example.playlistmaker.mvvm.db.data.converters

import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TrackDbConvertor {

    fun map(track: Track): TrackEntity {
        return TrackEntity(
            trackId = track.trackId,
            trackName = track.trackName,
            artworkUrl100 = track.artworkUrl100,
            artistName = track.artistName,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            trackDuration = track.trackTimeMillis,
            previewUrl = track.previewUrl,
            isFavorite = if (track.isFavorite) 1 else 0,
        )
    }

    fun map(track: TrackEntity, isFavorite: Boolean): Track {
        return Track(
            trackId = track.trackId,
            trackName = track.trackName,
            artworkUrl100 = track.artworkUrl100,
            artistName = track.artistName,
            trackTimeMillis = track.trackDuration,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = isFavorite,
        )
    }
}
