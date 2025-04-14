package com.example.playlistmaker

import com.example.playlistmaker.mvvm.audioplayer.data.TrackRepositoryImpl
import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.TrackInteractor
import com.example.playlistmaker.mvvm.search.domain.api.TrackRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track

object Creator {

    fun provideTrackRepository(track: Track): TrackRepository {
        return TrackRepositoryImpl(track)
    }

    fun providerTrackInteractor(trackRepository: TrackRepository): TrackInteractor {
        return TrackInteractor(trackRepository)
    }
}
