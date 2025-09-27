package com.example.playlistmaker.mvvm.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.mvvm.db.data.entity.TrackEntity

@Dao
interface TrackDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Update
    suspend fun updateTrack(trackEntity: TrackEntity)

    @Query("SELECT * FROM track_table WHERE isFavorite = 1 ORDER BY id DESC")
    suspend fun getTracksFavorite(): List<TrackEntity>

    @Query("SELECT * FROM track_table WHERE trackId IN (:trackIds) ORDER BY id DESC")
    suspend fun getTracksByIds(trackIds: List<Long>): List<TrackEntity>

    @Query("SELECT * FROM track_table WHERE trackId = :trackId")
    suspend fun getTrackById(trackId: Long): TrackEntity?

    @Query("DELETE FROM track_table WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: Long)
}
