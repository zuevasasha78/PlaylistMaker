package com.example.playlistmaker.mvvm.settings.data.di

import com.example.playlistmaker.mvvm.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.mvvm.settings.domain.SettingsRepository
import org.koin.dsl.module

val settingsRepositoryModule = module {

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get())
    }
}