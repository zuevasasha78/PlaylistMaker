package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_MAKER_PREFERENCES = "playlist_maker_preferences"
const val DARK_THEME_KEY = "dark_theme_key"

class App : Application() {

    var isDarkTheme = false
    var sharedPrefs: SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE)

        var darkThemeSP: String? = null
        sharedPrefs?.let {
            darkThemeSP = sharedPrefs!!.getString(DARK_THEME_KEY, "")
        }
        if (!darkThemeSP.isNullOrEmpty()) {
            isDarkTheme = darkThemeSP.toBoolean()
            switchTheme(isDarkTheme)
        } else {
            setDeviceTheme()
            switchTheme(isDarkTheme)
        }
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        isDarkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    private fun setDeviceTheme() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                isDarkTheme = false
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                isDarkTheme = true
            }
        }
    }
}
