package com.example.playlistmaker.mvvm.playlist.ui.di

import com.example.playlistmaker.mvvm.playlist.ui.view_modal.PlaylistViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val playlistUiModule = module {

    viewModel { (playlistId: Long) ->
        PlaylistViewModel(
            get { parametersOf(playlistId) },
            get(),
            get(),
            get(),
            get()
        )
    }
}
