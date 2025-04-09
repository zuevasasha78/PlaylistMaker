package com.example.playlistmaker.new.settings.data.impl

import android.app.Application.MODE_PRIVATE
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.playlistmaker.new.settings.domain.SettingsRepository
import com.example.playlistmaker.new.settings.domain.model.ThemeSettings

class SettingsRepositoryImpl(
    val context: Context
) : SettingsRepository {

    private var isDarkTheme = false
    private val sharedPrefs =
        context.getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)

    override fun getThemeSettings(): ThemeSettings {
        isDarkTheme = sharedPrefs.getBoolean(DARK_THEME_KEY, isSystemInDarkMode())
        return ThemeSettings(isDarkTheme)
    }

    override fun updateThemeSetting(themeSettings: ThemeSettings) {
        sharedPrefs.edit {
            putBoolean(DARK_THEME_KEY, themeSettings.isDarkTheme)
        }
        switchTheme(themeSettings)
    }

    override fun switchTheme(themeSettings: ThemeSettings) {
        isDarkTheme = themeSettings.isDarkTheme
        AppCompatDelegate.setDefaultNightMode(
            if (themeSettings.isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun isSystemInDarkMode(): Boolean {
        val currentNightMode =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    companion object {
        const val DARK_THEME_KEY = "dark_theme_key"
        const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
    }
}
