package com.example.playlistmaker.mvvm.library.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.databinding.LikedTracksFragmentBinding
import com.example.playlistmaker.mvvm.library.ui.view_model.LikedTracksViewModel
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.mvvm.search.ui.fragment.TrackAdapter
import com.example.playlistmaker.utils.debounce
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedTracksFragment : Fragment() {

    private val viewModel: LikedTracksViewModel by viewModel()
    private val viewBinding: LikedTracksFragmentBinding get() = _viewBinding!!
    private var _viewBinding: LikedTracksFragmentBinding? = null
    private lateinit var onTrackClickDebounce: (Track) -> Unit

    private val gson: Gson by inject()

    companion object {

        private const val CLICK_DEBOUNCE_DELAY = 1_000L

        fun newInstance() = LikedTracksFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = LikedTracksFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTrackDebouncer()

        viewModel.favoriteTrackListLiveData.observe(viewLifecycleOwner) { favoriteTracks ->
            if (favoriteTracks.isEmpty()) {
                showEmptyList(true)
            } else {
                showEmptyList(false)
                val favoriteTrackAdapter = TrackAdapter { track ->
                    startAudioPlayer(track)
                }
                favoriteTrackAdapter.setItems(favoriteTracks)
                val layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
                viewBinding.apply {
                    favoriteTrackList.layoutManager = layoutManager
                    favoriteTrackList.adapter = favoriteTrackAdapter
                }
            }
        }
    }

    private fun setTrackDebouncer() {
        onTrackClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { track ->
            startAudioPlayer(track)
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            viewBinding.emptyImageView.visibility = View.VISIBLE
            viewBinding.emptyListText.visibility = View.VISIBLE
            viewBinding.favoriteTrackList.visibility = View.GONE
        } else {
            viewBinding.emptyImageView.visibility = View.GONE
            viewBinding.emptyListText.visibility = View.GONE
            viewBinding.favoriteTrackList.visibility = View.VISIBLE
        }
    }

    private fun startAudioPlayer(track: Track) {
        onTrackClickDebounce(track)
        val trackJson = gson.toJson(track)
        val action =
            LibraryFragmentDirections.actionLibraryFragmentToAudioPlayerFragment(trackJson)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
