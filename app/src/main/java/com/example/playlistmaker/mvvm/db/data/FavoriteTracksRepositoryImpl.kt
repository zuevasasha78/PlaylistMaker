package com.example.playlistmaker.mvvm.db.data

import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.db.domain.FavoriteTracksRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteTracksRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor,
) : FavoriteTracksRepository {

    override fun getFavoriteTracks(): Flow<List<Track>> = flow {
        val trackEntity = appDatabase.getTrackDao().getTracks()
        emit(convertFromTrackEntity(trackEntity))
    }

    override suspend fun setFavoriteTracks(track: Track) {
        if (appDatabase.getTrackDao().getTrackById(track.trackId) == null) {
            appDatabase.getTrackDao().insertTrack(trackDbConvertor.map(track))
        }
    }

    override suspend fun deleteFavoriteTracks(trackId: Long) {
        appDatabase.getTrackDao().deleteTrack(trackId)
    }

    override suspend fun getFavoriteTrackById(trackId: Long): Track? {
        val trackEntity = appDatabase.getTrackDao().getTrackById(trackId)
        return if (trackEntity != null) {
            trackDbConvertor.map(trackEntity)
        } else {
            null
        }
    }

    private fun convertFromTrackEntity(track: List<TrackEntity>): List<Track> {
        return track.map { track -> trackDbConvertor.map(track) }
    }
}
