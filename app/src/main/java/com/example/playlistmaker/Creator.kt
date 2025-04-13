package com.example.playlistmaker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.playlistmaker.mvvm.audioplayer.data.TrackRepositoryImpl
import com.example.playlistmaker.mvvm.audioplayer.domain.use_case.TrackInteractor
import com.example.playlistmaker.mvvm.search.data.impl.TracksHistoryRepositoryImpl
import com.example.playlistmaker.mvvm.search.data.impl.TracksRepositoryImpl
import com.example.playlistmaker.mvvm.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.mvvm.search.domain.api.TrackRepository
import com.example.playlistmaker.mvvm.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.mvvm.search.domain.api.TracksRepository
import com.example.playlistmaker.mvvm.search.domain.impl.TracksInteractorImpl
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksHistoryInteractor
import com.example.playlistmaker.mvvm.search.domain.use_case.TracksInteractor
import com.example.playlistmaker.mvvm.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.mvvm.settings.domain.SettingsRepository
import com.example.playlistmaker.mvvm.sharing.domain.SharingUseCase
import com.example.playlistmaker.mvvm.sharing.domain.impl.SharingUseCaseImpl

object Creator {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun providerSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(appContext)
    }

    fun provideSharingUseCase(): SharingUseCase {
        return SharingUseCaseImpl(appContext)
    }

    fun provideTracksHistoryRepository(): TracksHistoryRepository {
        return TracksHistoryRepositoryImpl(
            appContext.getSharedPreferences(
                "app_prefs",
                MODE_PRIVATE
            )
        )
    }

    fun provideTracksHistoryInteractor(repository: TracksHistoryRepository): TracksHistoryInteractor {
        return TracksHistoryInteractor(repository)
    }

    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

    fun provideTrackRepository(track: Track): TrackRepository {
        return TrackRepositoryImpl(track)
    }

    fun providerTrackInteractor(trackRepository: TrackRepository): TrackInteractor {
        return TrackInteractor(trackRepository)
    }
}
