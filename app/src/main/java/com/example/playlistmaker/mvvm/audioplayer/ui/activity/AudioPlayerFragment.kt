package com.example.playlistmaker.mvvm.audioplayer.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentAudioPlayerBinding
import com.example.playlistmaker.mvvm.audioplayer.ui.view_model.AudioPlayerViewModel
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AudioPlayerFragment : Fragment() {

    private val args: AudioPlayerFragmentArgs by navArgs()
    private val gson: Gson by inject()

    private var viewBinding: FragmentAudioPlayerBinding? = null
    private val track: Track by lazy {
        val trackJson = args.trackData
        gson.fromJson(trackJson, Track::class.java)
    }
    private val viewModel: AudioPlayerViewModel by viewModel { parametersOf(track) }
    private val glide: RequestManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        setupListeners()
        viewModel.initPlayer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseOnLifecycle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun setupListeners() {
        viewBinding?.let {
            it.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            it.playButton.setOnClickListener {
                if (it.isEnabled) {
                    viewModel.playbackControl()
                }
            }
        }
    }

    private fun initView() {
        viewModel.track.observe(viewLifecycleOwner) { track ->
            viewBinding?.let {
                it.trackName.text = track.trackName
                it.artistName.text = track.artistName
                it.durationValue.text = track.trackTimeMillis
                it.yearValue.text = track.releaseDate
                it.genreName.text = track.primaryGenreName
                it.countryName.text = track.country

                if (!track.collectionName.isNullOrEmpty()) {
                    it.albumText.text = track.collectionName
                } else {
                    it.albumLine.isVisible = false
                }

                uploadImage(track.artworkUrl100)
            }
        }

        viewModel.playerState.observe(viewLifecycleOwner) { state ->
            viewBinding?.let {
                it.playButton.setImageResource(
                    if (state == 2) R.drawable.pause_button else R.drawable.play_button
                )
                it.playButton.isEnabled = (state != 0)
            }
        }

        viewModel.currentTime.observe(viewLifecycleOwner) { time ->
            viewBinding?.let {
                it.stopOnTime.text = time
            }
        }
    }

    private fun uploadImage(artworkUrl100: String) {
        val roundValue = 8
        val url = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        viewBinding?.let {
            glide.load(url)
                .placeholder(R.drawable.placeholder)
                .transform(
                    FitCenter(),
                    RoundedCorners(
                        roundValue * (resources.displayMetrics.density).toInt()
                    )
                )
                .into(it.trackImage)
        }
    }
}
