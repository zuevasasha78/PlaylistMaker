package com.example.playlistmaker.mvvm.settings.domain

import com.example.playlistmaker.mvvm.settings.domain.model.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(themeSettings: ThemeSettings)
    fun switchTheme(themeSettings: ThemeSettings)
}
