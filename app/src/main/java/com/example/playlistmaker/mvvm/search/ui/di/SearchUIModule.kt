package com.example.playlistmaker.mvvm.search.ui.di

import com.example.playlistmaker.mvvm.search.ui.view_model.SearchViewModel
import com.google.gson.Gson
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchUIModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }

    single {
        Gson()
    }
}