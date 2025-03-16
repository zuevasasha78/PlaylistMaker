package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.data.TracksHistoryRepositoryImpl
import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.GetTrackInteractor
import com.example.playlistmaker.domain.api.TracksHistoryInteractor
import com.example.playlistmaker.domain.api.TracksHistoryRepository
import com.example.playlistmaker.domain.api.TracksInteractor
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.impl.TracksInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

    fun providerGetTrackInteractor(): GetTrackInteractor {
        return GetTrackInteractor()
    }

    fun provideTracksHistoryRepository(sharedPreferences: SharedPreferences): TracksHistoryRepository {
        return TracksHistoryRepositoryImpl(sharedPreferences)
    }

    fun provideTracksHistoryInteractor(repository: TracksHistoryRepository): TracksHistoryInteractor {
        return TracksHistoryInteractor(repository)
    }
}
