package com.example.playlistmaker.mvvm.settings.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.mvvm.settings.domain.SettingsRepository
import com.example.playlistmaker.mvvm.settings.domain.model.ThemeSettings
import com.example.playlistmaker.mvvm.sharing.domain.SharingUseCase
import com.example.playlistmaker.mvvm.sharing.domain.model.EmailData

class SettingsViewModel(
    private val sharingUseCase: SharingUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var themeLiveData = MutableLiveData(settingsRepository.getThemeSettings().isDarkTheme)

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val sharingUseCase = Creator.provideSharingUseCase()
                val settingsRepository = Creator.providerSettingsRepository()

                SettingsViewModel(
                    sharingUseCase,
                    settingsRepository,
                )
            }
        }
    }

    fun getShareApp(): String =
        sharingUseCase.getShareLink()

    fun getLegalAgreement(): String =
        sharingUseCase.getLegalAgreement()

    fun getSupportData(): EmailData =
        sharingUseCase.getSupportData()

    fun getThemeSettings(): LiveData<Boolean> {
        return themeLiveData
    }

    fun updateThemeSetting(isDarkTheme: Boolean) {
        themeLiveData.postValue(isDarkTheme)
        settingsRepository.updateThemeSetting(ThemeSettings(isDarkTheme))
    }
}

