package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.mvvm.audioplayer.data.di.audioplayerDataModule
import com.example.playlistmaker.mvvm.audioplayer.domain.di.audioplayerDomainModule
import com.example.playlistmaker.mvvm.audioplayer.ui.di.audioplayerVMModule
import com.example.playlistmaker.mvvm.db.data.di.dbDataModule
import com.example.playlistmaker.mvvm.db.domain.di.dbDomainModule
import com.example.playlistmaker.mvvm.di.appModule
import com.example.playlistmaker.mvvm.library.ui.di.createPlaylistVMModule
import com.example.playlistmaker.mvvm.library.ui.di.likedTracksVmModule
import com.example.playlistmaker.mvvm.library.ui.di.playlistVMModule
import com.example.playlistmaker.mvvm.search.data.di.searchDataModule
import com.example.playlistmaker.mvvm.search.domain.di.searchDomainModule
import com.example.playlistmaker.mvvm.search.ui.di.searchUIModule
import com.example.playlistmaker.mvvm.settings.data.di.settingsRepositoryModule
import com.example.playlistmaker.mvvm.settings.domain.SettingsRepository
import com.example.playlistmaker.mvvm.settings.ui.di.settingsVMModule
import com.example.playlistmaker.mvvm.sharing.domain.di.sharingDomainModule
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
                dbDataModule,
                dbDomainModule,
                sharingDomainModule,
                settingsRepositoryModule,
                settingsVMModule,
                searchDataModule,
                searchDomainModule,
                searchUIModule,
                audioplayerDataModule,
                audioplayerDomainModule,
                audioplayerVMModule,
                playlistVMModule,
                likedTracksVmModule,
                createPlaylistVMModule,
                createPlaylistVMModule,
            )
        }

        val themeSettings = providerSettingsRepository.getThemeSettings()
        providerSettingsRepository.switchTheme(themeSettings)
    }
}
