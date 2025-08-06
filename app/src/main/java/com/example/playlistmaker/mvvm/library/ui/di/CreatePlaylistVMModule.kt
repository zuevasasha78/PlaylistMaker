package com.example.playlistmaker.mvvm.library.ui.di

import com.example.playlistmaker.mvvm.library.ui.fragment.createrplaylist.CreatePlaylistViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createPlaylistVMModule = module {

    viewModel {
        CreatePlaylistViewModel(get())
    }
}
