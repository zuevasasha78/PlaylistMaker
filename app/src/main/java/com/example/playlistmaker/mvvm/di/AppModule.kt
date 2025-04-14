package com.example.playlistmaker.mvvm.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("playlist_maker_preferences", MODE_PRIVATE)
    }
}