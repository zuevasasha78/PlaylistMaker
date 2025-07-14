package com.example.playlistmaker.mvvm.db.domain.di

import com.example.playlistmaker.mvvm.db.data.SavedTracksRepositoryImpl
import com.example.playlistmaker.mvvm.db.domain.SavedTracksInteractor
import com.example.playlistmaker.mvvm.db.domain.SavedTracksInteractorImpl
import com.example.playlistmaker.mvvm.db.domain.SavedTracksRepository
import org.koin.dsl.module

val dbDomainModule = module {

    single<SavedTracksRepository> {
        SavedTracksRepositoryImpl(get(), get())
    }

    single<SavedTracksInteractor> {
        SavedTracksInteractorImpl(get())
    }
}
