package com.example.playlistmaker.mvvm.search.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TrackAdapter(private val clickListener: TrackClickListener) :
    RecyclerView.Adapter<TrackViewHolder>() {

    private var trackItems: List<Track> = mutableListOf()

    fun setItems(tracks: List<Track>) {
        trackItems = tracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return trackItems.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        trackItems.getOrNull(position)?.let { track ->
            holder.bind(track)
            holder.itemView.setOnClickListener { clickListener.onTrackClick(track) }
        }
    }

    fun interface TrackClickListener {
        fun onTrackClick(track: Track)
    }
}
