package com.example.playlistmaker.mvvm.audioplayer.data.di

import com.example.playlistmaker.mvvm.audioplayer.data.TrackRepositoryImpl
import com.example.playlistmaker.mvvm.search.domain.api.TrackRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import org.koin.dsl.module

val audioplayerDataModule = module {

    factory<TrackRepository> { (track: Track) ->
        TrackRepositoryImpl(track)
    }
}