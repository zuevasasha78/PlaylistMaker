package com.example.playlistmaker.mvvm.library.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.PlaylistFragmentBinding
import com.example.playlistmaker.mvvm.library.ui.view_model.PlaylistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {

    private val viewModel: PlaylistViewModel by viewModel()
    private val viewBinding: PlaylistFragmentBinding get() = _viewBinding!!
    private var _viewBinding: PlaylistFragmentBinding? = null

    companion object {

        fun newInstance() = PlaylistFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.playlistLiveData.observe(viewLifecycleOwner) { playlist ->
            if (playlist.isEmpty()) {
                viewBinding.emptyImageView.visibility = View.VISIBLE
                viewBinding.emptyListText.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
