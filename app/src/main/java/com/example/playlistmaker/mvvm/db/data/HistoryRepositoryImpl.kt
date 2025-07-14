package com.example.playlistmaker.mvvm.db.data

import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.db.domain.HistoryRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor,
) : HistoryRepository {

    override fun historyTracks(): Flow<List<Track>> = flow {
        val trackEntity = appDatabase.getTrackDao().getTracks()
        emit(convertFromTrackEntity(trackEntity))
    }

    private fun convertFromTrackEntity(track: List<TrackEntity>): List<Track> {
        return track.map { movie -> trackDbConvertor.map(movie) }
    }
}
