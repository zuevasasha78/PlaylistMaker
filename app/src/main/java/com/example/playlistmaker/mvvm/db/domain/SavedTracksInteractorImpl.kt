package com.example.playlistmaker.mvvm.db.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class SavedTracksInteractorImpl(
    private val savedTracksRepository: SavedTracksRepository
) : SavedTracksInteractor {

    override fun savedTracksTracks(): Flow<List<Track>> {
        return savedTracksRepository.savedTracks()
    }
}
