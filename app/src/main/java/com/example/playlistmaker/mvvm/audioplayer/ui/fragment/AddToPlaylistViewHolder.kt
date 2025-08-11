package com.example.playlistmaker.mvvm.audioplayer.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistListItemViewBinding
import com.example.playlistmaker.mvvm.library.domain.models.Playlist

class AddToPlaylistViewHolder(private val viewBinding: PlaylistListItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(playlist: Playlist) {
        viewBinding.playlistName.text = playlist.name
        viewBinding.trackAmount.text = itemView.context.resources.getQuantityString(
            R.plurals.track_amount,
            playlist.tracksAmount,
            playlist.tracksAmount
        )
        val roundValue = 2
        Glide.with(itemView.context)
            .load(playlist.coverImageUrl)
            .placeholder(R.drawable.placeholder)
            .transform(
                CenterCrop(),
                RoundedCorners(
                    roundValue * (itemView.context.resources.displayMetrics.density).toInt()
                )
            )
            .into(viewBinding.playlistImage)
    }

    companion object {
        fun from(parent: ViewGroup): AddToPlaylistViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PlaylistListItemViewBinding.inflate(layoutInflater, parent, false)
            return AddToPlaylistViewHolder(binding)
        }
    }
}
