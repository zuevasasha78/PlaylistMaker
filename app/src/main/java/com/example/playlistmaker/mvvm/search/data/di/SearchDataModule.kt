package com.example.playlistmaker.mvvm.search.data.di

import com.example.playlistmaker.mvvm.search.data.impl.TracksHistoryRepositoryImpl
import com.example.playlistmaker.mvvm.search.data.impl.TracksRepositoryImpl
import com.example.playlistmaker.mvvm.search.data.network.ITunesService
import com.example.playlistmaker.mvvm.search.data.network.NetworkClient
import com.example.playlistmaker.mvvm.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.mvvm.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.mvvm.search.domain.api.TracksRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchDataModule = module {

    single<TracksHistoryRepository> {
        TracksHistoryRepositoryImpl(get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ITunesService::class.java) }
}
