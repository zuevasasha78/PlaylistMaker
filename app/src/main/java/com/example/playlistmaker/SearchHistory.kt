package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.network.data.Track
import com.google.gson.Gson

class SearchHistory(private val sharedPreferences: SharedPreferences) {

    fun getTrackList(): MutableList<Track> {
        val tracksSt = sharedPreferences.getString(TRACKS_HISTORY_KEY, null)
        var tracks = stringToList<MutableList<Track>>(tracksSt)
        if (tracks.isNullOrEmpty()) tracks = mutableListOf()
        return tracks
    }

    fun clearTrackList() {
        sharedPreferences.edit().putString(
            TRACKS_HISTORY_KEY,
            null
        ).apply()
        println()
    }

    fun updateTrackList(trackListHistory: MutableList<Track>) {
        sharedPreferences.edit().putString(
            TRACKS_HISTORY_KEY,
            Gson().toJson(trackListHistory)
        ).apply()
    }

    companion object {
        const val TRACKS_HISTORY_KEY = "track_history_key"
    }
}
