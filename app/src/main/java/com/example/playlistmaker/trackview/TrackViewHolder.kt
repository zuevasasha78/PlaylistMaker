package com.example.playlistmaker.trackview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.network.data.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackNameView: TextView = itemView.findViewById(R.id.trackName)
    private val artistNameView: TextView = itemView.findViewById(R.id.artistName)
    private val trackTimeView: TextView = itemView.findViewById(R.id.trackTime)
    private val trackImageView: ImageView = itemView.findViewById(R.id.trackImage)

    fun bind(track: Track) {
        trackNameView.text = track.trackName
        artistNameView.text = track.artistName

        val time = track.trackTimeMillis
        if (time != null) {
            trackTimeView.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(time)
        }

        val roundValue = 2
        Glide.with(itemView.context)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .transform(
                FitCenter(),
                RoundedCorners(
                    roundValue * (itemView.context.resources.displayMetrics.density).toInt()
                )
            )
            .into(trackImageView)
    }
}

