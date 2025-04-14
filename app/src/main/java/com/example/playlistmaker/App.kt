package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.mvvm.settings.di.settingsModule
import com.example.playlistmaker.mvvm.settings.di.settingsRepositoryModule
import com.example.playlistmaker.mvvm.sharing.di.sharingDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                sharingDomainModule,
                settingsRepositoryModule,
                settingsModule
            )
        }

        Creator.init(this)
        val providerSettingsRepository = Creator.providerSettingsRepository()
        val themeSettings = providerSettingsRepository.getThemeSettings()
        providerSettingsRepository.switchTheme(themeSettings)
    }
}
