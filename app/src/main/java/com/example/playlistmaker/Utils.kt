package com.example.playlistmaker

import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View

fun makeButtonRound(button: View) {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE

    val cornerRadiusInPx = button.resources.getDimensionPixelSize(R.dimen.radius_update_button).toFloat()
    drawable.cornerRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInPx, button.resources.displayMetrics
    )
    button.background = drawable
}
