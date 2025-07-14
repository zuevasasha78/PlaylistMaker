package com.example.playlistmaker.mvvm.search.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.mvvm.search.domain.models.Track

class TrackViewHolder(private val viewBinding: TrackViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(track: Track) {
        viewBinding.trackName.text = track.trackName
        viewBinding.artistName.text = track.artistName
        viewBinding.trackTime.text = track.trackTimeMillis

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
            .into(viewBinding.trackImage)
    }

    companion object {
        fun from(parent: ViewGroup): TrackViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TrackViewBinding.inflate(layoutInflater, parent, false)
            return TrackViewHolder(binding)
        }
    }
}
