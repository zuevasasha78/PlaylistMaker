package com.example.playlistmaker.mvvm.db.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.mvvm.db.data.dao.TrackDao
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity

@Database(
    version = 1,
    entities = [
        TrackEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTrackDao(): TrackDao
}
