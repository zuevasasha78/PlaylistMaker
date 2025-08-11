package com.example.playlistmaker.mvvm.playlist.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.playlist.ui.view_modal.PlaylistViewModel
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.mvvm.search.ui.fragment.TrackAdapter
import com.example.playlistmaker.utils.convertMinAndSecToLong
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
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
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnBackButtonListener()
        setOnShareButtonListener()
        setData()
    }

    private fun setOnShareButtonListener() {
        viewModel.playlistLiveData.observe(viewLifecycleOwner) { playlist ->
            if (playlist.tracksAmount != 0) {
                viewBinding.sharePlaylist.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            viewModel.getSharePlaylistData(
                                playlist.name,
                                getTrackAmount(playlist.tracksAmount)
                            )
                        )
                    }
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_app)))
                }
            } else {
                val message = getString(R.string.toast_share_empty_playlist)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.tracklistBottomSheet)
        bottomSheetBehavior.state = STATE_COLLAPSED
    }

    private fun setData() {
        viewModel.playlistLiveData.observe(viewLifecycleOwner) { playlist ->
            setPlaylistData(playlist)

            if (playlist.tracksAmount != 0) {
                initBottomSheet()
                setTracksListData()
            } else {
                viewBinding.tracklistBottomSheet.isVisible = false
            }
        }
    }

    private fun setPlaylistData(playlist: Playlist) {
        Glide.with(requireContext())
            .load(playlist.coverImageUrl)
            .placeholder(R.drawable.placeholder)
            .transform(
                CenterCrop(),
            )
            .into(viewBinding.coverImage)
        viewBinding.playlistName.text = playlist.name
        viewBinding.playlistDescription.text = playlist.description
        setPlaylistDuration(0)
        viewBinding.trackAmount.text = getTrackAmount(playlist.tracksAmount)
    }

    private fun getTrackAmount(tracksAmount: Int): String = requireContext().resources.getQuantityString(
        R.plurals.track_amount,
        tracksAmount,
        tracksAmount
    )

    private fun setTracksListData() {
        viewModel.tracksListLiveData.observe(viewLifecycleOwner) { tracksList ->
            val playlistDuration = (tracksList.sumOf {
                convertMinAndSecToLong(it.trackTimeMillis)
            } / 1000 / 60).toInt()
            setPlaylistDuration(playlistDuration)

            val trackAdapter = TrackAdapter(
                clickListener = { track ->
                    openAudioPlayer(track)
                },
                longClickListener = { track ->
                    showDialog(track)
                }
            )
            trackAdapter.setItems(tracksList)
            val layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            viewBinding.apply {
                tracklist.layoutManager = layoutManager
                tracklist.adapter = trackAdapter
            }
        }
    }

    private fun showDialog(track: Track) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_remove_track_title)
            .setMessage("")
            .setNegativeButton(R.string.dialog_remove_track_no) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.dialog_remove_track_yes) { dialog, which ->
                viewModel.removeTrackFromPlaylist(track)
            }
            .show()
    }

    private fun setPlaylistDuration(duration: Int) {
        viewBinding.playlistDuration.text = requireContext().resources.getQuantityString(
            R.plurals.minute_amount,
            duration,
            duration
        )
    }

    private fun openAudioPlayer(track: Track) {
        val trackJson = Gson().toJson(track)
        val action =
            PlaylistFragmentDirections.actionPlaylistFragmentToAudioPlayerFragment(trackJson)
        findNavController().navigate(action)
    }

    private fun setOnBackButtonListener() {
        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
