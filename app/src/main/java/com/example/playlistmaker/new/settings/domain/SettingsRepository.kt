package com.example.playlistmaker.new.settings.domain

import com.example.playlistmaker.new.settings.domain.model.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}
