package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.mvvm.di.appModule
import com.example.playlistmaker.mvvm.search.di.searchDataModule
import com.example.playlistmaker.mvvm.search.di.searchDomainModule
import com.example.playlistmaker.mvvm.search.di.searchVMModule
import com.example.playlistmaker.mvvm.settings.di.settingsRepositoryModule
import com.example.playlistmaker.mvvm.settings.di.settingsVMModule
import com.example.playlistmaker.mvvm.settings.domain.SettingsRepository
import com.example.playlistmaker.mvvm.sharing.di.sharingDomainModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    private val providerSettingsRepository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                sharingDomainModule,
                settingsRepositoryModule,
                settingsVMModule,
                searchDataModule,
                searchDomainModule,
                searchVMModule,
            )
        }

        val themeSettings = providerSettingsRepository.getThemeSettings()
        providerSettingsRepository.switchTheme(themeSettings)
    }
}
