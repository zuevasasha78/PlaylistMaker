package com.example.playlistmaker.mvvm.db.di

import androidx.room.Room
import com.example.playlistmaker.mvvm.db.data.AppDatabase
import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import org.koin.dsl.module

val dbDataModule = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "database.db")
            .build()
    }

    factory { TrackDbConvertor() }
}
