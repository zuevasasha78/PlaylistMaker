package com.example.playlistmaker.mvvm.playlist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.utils.durationFormat
import com.google.gson.Gson

class PlaylistFragment : Fragment() {

    private val viewModel: PlaylistViewModel by viewModels()

    private val viewBinding: FragmentPlaylistBinding get() = _viewBinding!!
    private var _viewBinding: FragmentPlaylistBinding? = null

    private val args: PlaylistFragmentArgs by navArgs()
    private val gson = Gson()
    private val playlist: Playlist by lazy {
        val playlistJson = args.playlistData
        gson.fromJson(playlistJson, Playlist::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnBackButtonListener()
        setData()
    }

    private fun setData() {
        viewBinding.playlistName.text = playlist.name
        viewBinding.playlistDescription.text = playlist.description
        viewBinding.playlistDuration.text = durationFormat(playlist.tracksAmount * 30 * 1_000)
        viewBinding.trackAmount.text = playlist.tracksAmount.toString()
    }

    private fun setOnBackButtonListener() {
        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
