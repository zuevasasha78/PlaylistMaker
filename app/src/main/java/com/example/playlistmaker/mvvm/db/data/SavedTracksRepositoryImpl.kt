package com.example.playlistmaker.mvvm.db.data

import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity
import com.example.playlistmaker.mvvm.db.domain.SavedTracksRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SavedTracksRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor,
) : SavedTracksRepository {

    override fun savedTracks(): Flow<List<Track>> = flow {
        val trackEntity = appDatabase.getTrackDao().getTracks()
        emit(convertFromTrackEntity(trackEntity))
    }

    private fun convertFromTrackEntity(track: List<TrackEntity>): List<Track> {
        return track.map { movie -> trackDbConvertor.map(movie) }
    }
}
