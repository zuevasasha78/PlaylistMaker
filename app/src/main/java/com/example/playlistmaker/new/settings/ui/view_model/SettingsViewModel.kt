package com.example.playlistmaker.new.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.MyApplication
import com.example.playlistmaker.new.settings.domain.SettingsRepository
import com.example.playlistmaker.new.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val application: Application,
    private val sharingInteractor: SharingInteractor,
    private val settingsRepository: SettingsRepository
) : AndroidViewModel(application) {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // 3
                val sharingInteractor =
                    (this[APPLICATION_KEY] as MyApplication).provideSharingInteractor()
                val settingsRepository = Creator.providerSettingsRepository()

                SettingsViewModel(
                    (this[APPLICATION_KEY] as MyApplication),
                    sharingInteractor,
                    settingsRepository,
                )
            }
        }
    }
}

