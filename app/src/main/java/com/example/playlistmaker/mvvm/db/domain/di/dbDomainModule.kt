package com.example.playlistmaker.mvvm.db.domain.di

import com.example.playlistmaker.mvvm.db.data.HistoryRepositoryImpl
import com.example.playlistmaker.mvvm.db.domain.HistoryInteractor
import com.example.playlistmaker.mvvm.db.domain.HistoryInteractorImpl
import com.example.playlistmaker.mvvm.db.domain.HistoryRepository
import org.koin.dsl.module

val dbDomainModule = module {

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }

    single<HistoryInteractor> {
        HistoryInteractorImpl(get())
    }
}
