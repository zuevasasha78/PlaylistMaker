package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(
    private val historyRepository: HistoryRepository
) : HistoryInteractor {

    override fun historyTracks(): Flow<List<Track>> {
        return historyRepository.historyTracks()
    }
}
