package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.network.data.Track
import com.google.gson.Gson

class SearchHistory(val sharedPreferences: SharedPreferences) {

    fun getTrackList(): MutableList<Track> {
        val tracksSt = sharedPreferences.getString(TRACKS_HISTORY_KEY, null)
        return stringToTrackList(tracksSt)
    }

    fun clearTrackList() {
        sharedPreferences.edit().putString(
            TRACKS_HISTORY_KEY,
            null
        ).apply()
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
