package com.example.playlistmaker.trackview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.network.data.Track

class TrackHistoryAdapter(
    private val tracksHistory: List<Track>,
) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracksHistory.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracksHistory[position]
        holder.bind(track)
    }
}
