package com.example.playlistmaker.mvvm.playlist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.mvvm.playlist.ui.view_modal.PlaylistViewModel
import com.example.playlistmaker.utils.durationFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlaylistFragment : Fragment() {

    private val viewBinding: FragmentPlaylistBinding get() = _viewBinding!!
    private var _viewBinding: FragmentPlaylistBinding? = null

    private val args: PlaylistFragmentArgs by navArgs()
    private val playlistId: Long by lazy {
        args.playlistId
    }

    private val viewModel: PlaylistViewModel by viewModel { parametersOf(playlistId) }

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
        viewModel.playlistLiveData.observe(viewLifecycleOwner) { playlist ->
            Glide.with(requireContext())
                .load(playlist.coverImageUrl)
                .placeholder(R.drawable.placeholder)
                .transform(
                    CenterCrop(),
                )
                .into(viewBinding.coverImage)
            viewBinding.playlistName.text = playlist.name
            viewBinding.playlistDescription.text = playlist.description
            viewBinding.playlistDuration.text = durationFormat(playlist.tracksAmount * 30 * 1_000)
            viewBinding.trackAmount.text = playlist.tracksAmount.toString()
        }
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
