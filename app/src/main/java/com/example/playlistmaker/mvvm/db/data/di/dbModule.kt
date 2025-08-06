package com.example.playlistmaker.mvvm.db.data.di

import androidx.room.Room
import com.example.playlistmaker.mvvm.db.data.AppDatabase
import com.example.playlistmaker.mvvm.db.data.converters.PlaylistDbConvertor
import com.example.playlistmaker.mvvm.db.data.converters.TrackDbConvertor
import com.example.playlistmaker.mvvm.db.data.dao.PlaylistDao
import com.example.playlistmaker.mvvm.db.data.dao.TrackDao
import org.koin.dsl.module

val dbDataModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "database.db")
            .build()
    }

    single<TrackDao> {
        get<AppDatabase>().getTrackDao()
    }

    single<PlaylistDao> {
        get<AppDatabase>().getPlaylistDao()
    }

    factory { TrackDbConvertor() }
    factory { PlaylistDbConvertor() }
}
