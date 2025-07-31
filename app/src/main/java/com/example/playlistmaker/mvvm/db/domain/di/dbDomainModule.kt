package com.example.playlistmaker.mvvm.db.domain.di

import com.example.playlistmaker.mvvm.db.data.FavoriteTracksRepositoryImpl
import com.example.playlistmaker.mvvm.db.domain.FavoriteTracksRepository
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
}
