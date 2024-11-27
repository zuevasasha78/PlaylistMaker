package com.example.playlistmaker.trackview

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.network.data.Track

class TrackAdapter(
    var trackList: MutableList<Track>, private val onClick: (Track) -> Unit
) :
    RecyclerView.Adapter<TrackViewHolder>() {

    companion object {
        private const val DEBOUNCE_DELAY = 1_000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isClickable = true
    private var runnable: Runnable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = trackList[position]
        holder.bind(track)

        runnable?.let { handler.removeCallbacks(it) }
        holder.itemView.setOnClickListener {
            if (isClickable) {
                isClickable = false
                onClick(track)
                runnable = Runnable {
                    isClickable = true
                }
                handler.postDelayed(runnable!!, DEBOUNCE_DELAY)
            }
        }
    }
}
