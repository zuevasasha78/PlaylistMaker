package com.example.playlistmaker.mvvm.audioplayer.domain.di

import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.SaveTrackToPlaylistUseCase
import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.SaveTrackToPlaylistUseCaseImpl
import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.TrackInteractor
import com.example.playlistmaker.mvvm.search.domain.models.Track
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val audioplayerDomainModule = module {

    factory { (track: Track) ->
        TrackInteractor(get { parametersOf(track) })
    }

    single<SaveTrackToPlaylistUseCase> {
        SaveTrackToPlaylistUseCaseImpl(get(), get())
    }
}
