package com.example.playlistmaker.mvvm.db.data

import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import com.example.playlistmaker.mvvm.db.data.dao.TrackDao
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryDbImpl(
    private val trackDao: TrackDao,
    private val trackDbConvertor: TrackDbConvertor,
) : TracksRepositoryDb {

    override fun getFavoriteTracks(): Flow<List<Track>> = flow {
        val trackEntity = trackDao.getTracksFavorite()
        emit(convertFromTrackEntity(trackEntity))
    }

    override fun getTracksByIds(trackIds: List<Long>): Flow<List<Track>> = flow {
        val trackEntity = trackDao.getTracksByIds(trackIds)
        emit(convertFromTrackEntity(trackEntity))
    }

    override suspend fun setTracks(track: Track) {
        val track = trackDbConvertor.map(track)
        if (trackDao.getTrackById(track.trackId) == null) {
            trackDao.insertTrack(track)
        } else {
            trackDao.updateTrack(track)
        }
    }

    override suspend fun deleteTracks(trackId: Long) {
        trackDao.deleteTrack(trackId)
    }

    override suspend fun getTrackById(trackId: Long): Track? {
        val trackEntity = trackDao.getTrackById(trackId)
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
