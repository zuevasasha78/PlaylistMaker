package com.example.playlistmaker.mvvm.search.di

import com.example.playlistmaker.mvvm.search.data.impl.TracksHistoryRepositoryImpl
import com.example.playlistmaker.mvvm.search.data.impl.TracksRepositoryImpl
import com.example.playlistmaker.mvvm.search.data.network.NetworkClient
import com.example.playlistmaker.mvvm.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.mvvm.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.mvvm.search.domain.api.TracksRepository
import org.koin.dsl.module

val searchDataModule = module {

    single<TracksHistoryRepository> {
        TracksHistoryRepositoryImpl(get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient()
    }
}
