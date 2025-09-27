package com.example.playlistmaker.utils

import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.LocalTime
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

fun convertMinAndSecToLong(time: String): Long {
    val fullTime = "00:$time"
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val localTime = LocalTime.parse(fullTime, formatter)
    return localTime.toSecondOfDay() * 1000L
}

fun durationFormat(milliseconds: Int): String {
    val minutes = (milliseconds / 1000) / 60
    val seconds = (milliseconds / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}
