package com.example.playlistmaker.mvvm.playlist.domain.di

import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCaseImpl
import org.koin.dsl.module

val playlistDomainModule = module {

    single<GetPlaylistDataUseCase> {
        GetPlaylistDataUseCaseImpl(get())
    }
}
