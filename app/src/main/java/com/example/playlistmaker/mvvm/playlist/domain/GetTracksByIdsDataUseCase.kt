package com.example.playlistmaker.mvvm.playlist.domain

import com.example.playlistmaker.mvvm.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetTracksByIdsDataUseCase {

    fun execute(tracksIds: List<Long>): Flow<List<Track>>
}
