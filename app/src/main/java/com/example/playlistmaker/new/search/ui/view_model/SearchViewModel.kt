package com.example.playlistmaker.new.search.ui.view_model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.MyApplication

class SearchViewModel(
    myApplication: MyApplication,
) : AndroidViewModel(myApplication) {

    private val tracksInteractor = Creator.provideTracksInteractor()

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as MyApplication)
                val provideSharedPreferences = Creator.provideSharedPreferences(app)
                val provideTracksInteractor =
                    Creator.provideTracksHistoryRepository(provideSharedPreferences)
                val tracksHistoryInteractor =
                    Creator.provideTracksHistoryInteractor(provideTracksInteractor)

                SearchViewModel(
                    app
                )
            }
        }
    }
}