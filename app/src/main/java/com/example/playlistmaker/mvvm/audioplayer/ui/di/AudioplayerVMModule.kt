package com.example.playlistmaker.mvvm.audioplayer.ui.di

import com.example.playlistmaker.mvvm.audioplayer.ui.view_model.AudioPlayerViewModel
import com.example.playlistmaker.mvvm.search.domain.models.Track
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val audioplayerVMModule = module {

    viewModel { (track: Track) ->
        AudioPlayerViewModel(get { parametersOf(track) })
    }
}