package com.example.playlistmaker.mvvm.search.ui.di

import com.example.playlistmaker.mvvm.search.ui.view_model.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchVMModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }
}