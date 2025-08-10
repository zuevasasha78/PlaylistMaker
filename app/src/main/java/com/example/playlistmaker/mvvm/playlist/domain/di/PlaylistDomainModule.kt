package com.example.playlistmaker.mvvm.playlist.domain.di

import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCaseImpl
import com.example.playlistmaker.mvvm.playlist.domain.GetTracksByIdsDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.GetTracksByIdsDataUseCaseImpl
import org.koin.dsl.module

val playlistDomainModule = module {

    single<GetPlaylistDataUseCase> {
        GetPlaylistDataUseCaseImpl(get())
    }

    single<GetTracksByIdsDataUseCase> {
        GetTracksByIdsDataUseCaseImpl(get())
    }
}
