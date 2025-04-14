package com.example.playlistmaker

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Creator.init(this)
        val providerSettingsRepository = Creator.providerSettingsRepository()
        val themeSettings = providerSettingsRepository.getThemeSettings()
        providerSettingsRepository.switchTheme(themeSettings)
    }
}
