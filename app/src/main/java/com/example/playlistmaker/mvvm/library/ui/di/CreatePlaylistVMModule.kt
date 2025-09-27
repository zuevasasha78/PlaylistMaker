package com.example.playlistmaker.mvvm.library.ui.di

import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.library.ui.view_model.createrplaylist.CreatePlaylistViewModel
import com.example.playlistmaker.mvvm.library.ui.view_model.createrplaylist.EditableCreatePlaylistViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createPlaylistVMModule = module {

    viewModel {
        CreatePlaylistViewModel(get())
    }

    viewModel { (playlist: Playlist) ->
        EditableCreatePlaylistViewModel(
            playlist,
            get(),
            get()
        )
    }
}
