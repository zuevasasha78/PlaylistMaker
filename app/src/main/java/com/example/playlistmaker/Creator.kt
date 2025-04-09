package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.playlistmaker.data.TrackRepositoryImpl
import com.example.playlistmaker.new.search.data.impl.TracksHistoryRepositoryImpl
import com.example.playlistmaker.new.search.data.impl.TracksRepositoryImpl
import com.example.playlistmaker.new.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.new.search.domain.api.TrackRepository
import com.example.playlistmaker.new.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.new.search.domain.api.TracksRepository
import com.example.playlistmaker.new.search.domain.impl.TracksInteractorImpl
import com.example.playlistmaker.domain.use_case.TrackInteractor
import com.example.playlistmaker.new.search.domain.use_case.TracksHistoryInteractor
import com.example.playlistmaker.new.search.domain.use_case.TracksInteractor
import com.example.playlistmaker.new.sharing.domain.SharingUseCase
import com.example.playlistmaker.new.sharing.domain.impl.SharingUseCaseImpl

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

    fun provideSharingInteractor(): SharingUseCase {
        return SharingUseCaseImpl()
    }

    fun provideTrackRepository(intent: Intent): TrackRepository {
        return TrackRepositoryImpl(intent)
    }

    fun providerTrackInteractor(trackRepository: TrackRepository): TrackInteractor {
        return TrackInteractor(trackRepository)
    }

    fun provideTracksHistoryRepository(sharedPreferences: SharedPreferences): TracksHistoryRepository {
        return TracksHistoryRepositoryImpl(sharedPreferences)
    }

    fun provideTracksHistoryInteractor(repository: TracksHistoryRepository): TracksHistoryInteractor {
        return TracksHistoryInteractor(repository)
    }
}
