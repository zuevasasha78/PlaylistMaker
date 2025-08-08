package com.example.playlistmaker.mvvm.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.mvvm.db.data.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlist_table ORDER BY id DESC")
    suspend fun getPlaylists(): List<PlaylistEntity>

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity): Int
}
