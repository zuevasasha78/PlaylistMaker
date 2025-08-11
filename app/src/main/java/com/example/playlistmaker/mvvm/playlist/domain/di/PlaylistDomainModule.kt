package com.example.playlistmaker.mvvm.playlist.domain.di

import com.example.playlistmaker.mvvm.playlist.domain.DeletePlaylistUseCase
import com.example.playlistmaker.mvvm.playlist.domain.DeletePlaylistUseCaseImpl
import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.GetPlaylistDataUseCaseImpl
import com.example.playlistmaker.mvvm.playlist.domain.GetTracksByIdsDataUseCase
import com.example.playlistmaker.mvvm.playlist.domain.GetTracksByIdsDataUseCaseImpl
import com.example.playlistmaker.mvvm.playlist.domain.RemoveTrackFromPlaylistUseCase
import com.example.playlistmaker.mvvm.playlist.domain.RemoveTrackFromPlaylistUseCaseImpl
import org.koin.dsl.module

val playlistDomainModule = module {

    single<GetPlaylistDataUseCase> {
        GetPlaylistDataUseCaseImpl(get())
    }

    single<GetTracksByIdsDataUseCase> {
        GetTracksByIdsDataUseCaseImpl(get())
    }

    single<RemoveTrackFromPlaylistUseCase> {
        RemoveTrackFromPlaylistUseCaseImpl(get(), get())
    }

    single<DeletePlaylistUseCase> {
        DeletePlaylistUseCaseImpl(get(), get())
    }
}
