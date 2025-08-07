package com.example.playlistmaker.mvvm.library.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.mvvm.library.domain.models.Playlist

class PlaylistAdapter(private val clickListener: PlaylistClickListener) : RecyclerView.Adapter<PlaylistViewHolder>() {

    private var playlistItems: List<Playlist> = mutableListOf()

    fun setItems(playlist: List<Playlist>) {
        playlistItems = playlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        playlistItems.getOrNull(position)?.let { playlist ->
            holder.bind(playlist)
            holder.itemView.setOnClickListener { clickListener.onPlaylistClick(playlist) }
        }
    }

    override fun getItemCount(): Int {
        return playlistItems.size
    }

    fun interface PlaylistClickListener {
        fun onPlaylistClick(playlist: Playlist)
    }
}
