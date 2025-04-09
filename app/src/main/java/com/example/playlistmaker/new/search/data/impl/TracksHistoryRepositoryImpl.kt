package com.example.playlistmaker.new.search.data.impl

import android.content.SharedPreferences
import com.example.playlistmaker.new.search.domain.api.TracksHistoryRepository
import com.example.playlistmaker.new.search.domain.models.Track
import com.example.playlistmaker.stringToTrackList
import com.google.gson.Gson

class TracksHistoryRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : TracksHistoryRepository {

    override fun clearTrackList() {
        sharedPreferences.edit().putString(
            TRACKS_HISTORY_KEY,
            null
        ).apply()
    }

    override fun updateTrackList(trackListHistory: MutableList<Track>) {
        sharedPreferences.edit().putString(
            TRACKS_HISTORY_KEY,
            Gson().toJson(trackListHistory)
        ).apply()
    }

    override fun getTracksList(): MutableList<Track> {
        val tracksSt = sharedPreferences.getString(TRACKS_HISTORY_KEY, null)
        var tracks = stringToTrackList(tracksSt)
        if (tracks.isNullOrEmpty()) tracks = mutableListOf()
        return tracks
    }

    companion object {
        private const val TRACKS_HISTORY_KEY = "track_history_key"
    }
}