package com.example.playlistmaker.mvvm.search.domain.di

import com.example.playlistmaker.mvvm.search.domain.impl.TracksInteractorImpl
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksHistoryInteractor
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor
import org.koin.dsl.module

val searchDomainModule = module {

    single<TracksInteractor> {
        TracksInteractorImpl(get())
    }

    single {
        TracksHistoryInteractor(get())
    }
}
