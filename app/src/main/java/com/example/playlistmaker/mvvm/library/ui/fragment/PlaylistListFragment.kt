package com.example.playlistmaker.mvvm.library.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.databinding.PlaylistListFragmentBinding
import com.example.playlistmaker.mvvm.library.ui.view_model.PlaylistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistListFragment : Fragment() {

    private val viewModel: PlaylistViewModel by viewModel()
    private val viewBinding: PlaylistListFragmentBinding get() = _viewBinding!!
    private var _viewBinding: PlaylistListFragmentBinding? = null

    companion object {

        fun newInstance() = PlaylistListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = PlaylistListFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setPlaylistList()
        setNewPlaylistButtonListener()
    }

    private fun setNewPlaylistButtonListener() {
        viewBinding.newPlaylistButton.setOnClickListener {
            val action =
                LibraryFragmentDirections.actionLibraryFragmentToCreatePlaylistFragment()
            findNavController().navigate(action)
        }
    }

    private fun setPlaylistList() {
        viewModel.updatePlaylistList()
        viewModel.playlistLiveData.observe(viewLifecycleOwner) { playlist ->
            if (playlist.isEmpty()) {
                setVisibleEmptyList()
            } else {
                setVisiblePlaylist()
                val adapter = PlaylistAdapter {
                    openPlaylist(it.id)
                }
                val gridLayout = GridLayoutManager(requireContext(), 2)
                adapter.setItems(playlist)
                viewBinding.apply {
                    playlistList.layoutManager = gridLayout
                    playlistList.adapter = adapter
                }
            }
        }
    }

    private fun openPlaylist(playlistId: Long) {
        val action =
            LibraryFragmentDirections.actionLibraryFragmentToPlaylistFragment(playlistId)
        findNavController().navigate(action)
    }

    private fun setVisibleEmptyList() {
        viewBinding.emptyImageView.visibility = View.VISIBLE
        viewBinding.emptyListText.visibility = View.VISIBLE
        viewBinding.playlistList.visibility = View.GONE
    }

    private fun setVisiblePlaylist() {
        viewBinding.emptyImageView.visibility = View.GONE
        viewBinding.emptyListText.visibility = View.GONE
        viewBinding.playlistList.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
