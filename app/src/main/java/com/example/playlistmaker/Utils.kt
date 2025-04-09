package com.example.playlistmaker

import com.example.playlistmaker.new.search.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun stringToTrackList(jsonString: String?): MutableList<Track> {
    val gson = Gson()
    val listType = object : TypeToken<MutableList<Track>>() {}.type

    return if (jsonString.isNullOrEmpty()) {
        mutableListOf()
    } else {
        gson.fromJson(jsonString, listType)
    }
}

fun <T> stringToObject(jsonString: String?, clazz: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(jsonString, clazz)
}

fun convertMsToData(trackTimeMillis: Int, format: String): String {
    return trackTimeMillis.let {
        SimpleDateFormat(format, Locale.getDefault()).format(it)
    }
}

fun convertStringToData(time: String, format: String): String {
    val zonedDateTime = ZonedDateTime.parse(time)
    val formatter = DateTimeFormatter.ofPattern(format)
    return zonedDateTime.format(formatter)
}

fun durationFormat(milliseconds: Int): String {
    val minutes = (milliseconds / 1000) / 60
    val seconds = (milliseconds / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}
