package com.example.playlistmaker.mvvm.db.domain.di

import com.example.playlistmaker.mvvm.db.data.FavoriteTracksRepositoryImpl
import com.example.playlistmaker.mvvm.db.data.PlaylistsRepositoryImpl
import com.example.playlistmaker.mvvm.db.domain.FavoriteTracksRepository
import com.example.playlistmaker.mvvm.db.domain.PlaylistsRepository
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCase
import com.example.playlistmaker.mvvm.db.domain.use_case.CreatePlaylistUseCaseImpl
import com.example.playlistmaker.mvvm.db.domain.use_case.FavoriteTracksInteractor
import com.example.playlistmaker.mvvm.db.domain.use_case.FavoriteTracksInteractorImpl
import org.koin.dsl.module

val dbDomainModule = module {

    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(get(), get())
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
}
