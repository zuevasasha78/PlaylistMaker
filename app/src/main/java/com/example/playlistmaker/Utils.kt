package com.example.playlistmaker

import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import com.example.playlistmaker.network.data.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Locale

fun makeButtonRound(button: View) {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE

    val cornerRadiusInPx = button.resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
    drawable.cornerRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, button.resources.displayMetrics
    )
    button.background = drawable
}

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

fun convertTimeFormat(trackTimeMillis: Int?): String? {
    return trackTimeMillis?.let {
        SimpleDateFormat("mm:ss", Locale.getDefault()).format(it)
    }
}
