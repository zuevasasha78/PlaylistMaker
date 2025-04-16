package com.example.playlistmaker.mvvm.settings.ui.di

import com.example.playlistmaker.mvvm.settings.ui.view_model.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsVMModule = module {

    viewModel {
        SettingsViewModel(get(), get())
    }
}