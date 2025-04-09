package com.example.playlistmaker.new.settings.ui.view_model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.MyApplication
import com.example.playlistmaker.new.settings.domain.SettingsRepository
import com.example.playlistmaker.new.settings.domain.model.ThemeSettings
import com.example.playlistmaker.new.sharing.domain.SharingUseCase
import com.example.playlistmaker.new.sharing.domain.model.EmailData

class SettingsViewModel(
    val myApplication: MyApplication,
    private val sharingUseCase: SharingUseCase,
    private val settingsRepository: SettingsRepository
) : AndroidViewModel(myApplication) {

    private var themeLiveData = MutableLiveData(settingsRepository.getThemeSettings().isDarkTheme)

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as MyApplication)
                val sharingInteractor = Creator.provideSharingInteractor()
                val settingsRepository = app.providerSettingsRepository()

                SettingsViewModel(
                    (this[APPLICATION_KEY] as MyApplication),
                    sharingInteractor,
                    settingsRepository,
                )
            }
        }
    }

    fun getShareApp(): String =
        myApplication.getString(sharingUseCase.getShareLink())

    fun getLegalAgreement(): String =
        myApplication.getString(sharingUseCase.getLegalAgreement())

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

