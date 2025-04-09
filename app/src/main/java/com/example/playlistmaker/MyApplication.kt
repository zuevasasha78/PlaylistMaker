package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.new.settings.data.impl.SettingsRepositoryImpl
import com.example.playlistmaker.new.settings.domain.SettingsRepository

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val providerSettingsRepository = providerSettingsRepository()
        val themeSettings = providerSettingsRepository.getThemeSettings()
        providerSettingsRepository.switchTheme(themeSettings)
    }

    fun providerSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(applicationContext)
    }
}
