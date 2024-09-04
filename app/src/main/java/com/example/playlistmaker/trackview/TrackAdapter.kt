package com.example.playlistmaker.trackview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.network.data.Track

class TrackAdapter() : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    private var tracks: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {}
    }

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val trackNameView: TextView = itemView.findViewById(R.id.trackName)
        private val artistNameView: TextView = itemView.findViewById(R.id.artistName)
        private val trackTimeView: TextView = itemView.findViewById(R.id.trackTime)
        private val trackImageView: ImageView = itemView.findViewById(R.id.trackImage)

        fun bind(track: Track) {
            trackNameView.text = track.trackName
            artistNameView.text = track.artistName
            trackTimeView.text = track.trackTime

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
}
