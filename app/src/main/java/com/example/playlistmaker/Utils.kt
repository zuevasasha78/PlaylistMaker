package com.example.playlistmaker

import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun makeButtonRound(button: View) {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE

    val cornerRadiusInPx = button.resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
    drawable.cornerRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, button.resources.displayMetrics
    )
    button.background = drawable
}

fun <T> stringToList(jsonString: String?): T? {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(jsonString, type)
}

inline fun <reified T> stringToObject(jsonString: String?): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson<T>(jsonString, type)
}
