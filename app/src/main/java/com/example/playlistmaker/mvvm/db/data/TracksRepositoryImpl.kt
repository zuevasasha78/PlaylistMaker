package com.example.playlistmaker.mvvm.db.data

import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import com.example.playlistmaker.mvvm.db.data.dao.TrackDao
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.db.domain.TracksRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val trackDao: TrackDao,
    private val trackDbConvertor: TrackDbConvertor,
) : TracksRepository {

    override fun getTracks(): Flow<List<Track>> = flow {
        val trackEntity = trackDao.getTracks()
        emit(convertFromTrackEntity(trackEntity))
    }

    override fun getTracksByIds(): Flow<List<Track>> = flow {
        val trackEntity = trackDao.getTracks()
        emit(convertFromTrackEntity(trackEntity))
    }

    override suspend fun setTracks(track: Track) {
        if (trackDao.getTrackById(track.trackId) == null) {
            trackDao.insertTrack(trackDbConvertor.map(track))
        }
    }

    override suspend fun deleteTracks(trackId: Long) {
        trackDao.deleteTrack(trackId)
    }

    override suspend fun getTrackById(trackId: Long): Track? {
        val trackEntity = trackDao.getTrackById(trackId)
        return if (trackEntity != null) {
            trackDbConvertor.map(trackEntity, isFavorite = true)
        } else {
            null
        }
    }

    private fun convertFromTrackEntity(track: List<TrackEntity>): List<Track> {
        return track.map { track -> trackDbConvertor.map(track, true) }
    }
}
