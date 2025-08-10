package com.example.playlistmaker.mvvm.db.domain.di

import com.example.playlistmaker.mvvm.db.data.PlaylistsRepositoryImpl
import com.example.playlistmaker.mvvm.db.data.TracksRepositoryDbImpl
import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.db.domain.TracksRepositoryDb
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCase
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCaseImpl
import com.example.playlistmaker.mvvm.db.domain.use_case.FavoriteTracksInteractor
import com.example.playlistmaker.mvvm.db.domain.use_case.FavoriteTracksInteractorImpl
import com.example.playlistmaker.mvvm.db.domain.use_case.GetPlaylistListUseCase
import com.example.playlistmaker.mvvm.db.domain.use_case.GetPlaylistListUseCaseImpl
import org.koin.dsl.module

val dbDomainModule = module {

    single<TracksRepositoryDb> {
        TracksRepositoryDbImpl(get(), get())
    }

    single<FavoriteTracksInteractor> {
        FavoriteTracksInteractorImpl(get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get(), get())
    }

    single<CreatePlaylistUseCase> {
        CreatePlaylistUseCaseImpl(get())
    }

    single<GetPlaylistListUseCase> {
        GetPlaylistListUseCaseImpl(get())
    }

}
