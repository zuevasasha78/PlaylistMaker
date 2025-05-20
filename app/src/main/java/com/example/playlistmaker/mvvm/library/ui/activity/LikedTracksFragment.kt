package com.example.playlistmaker.mvvm.library.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.LikedTracksFragmentBinding
import com.example.playlistmaker.mvvm.library.ui.view_model.LikedTracksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikedTracksFragment : Fragment() {

    private val viewModel: LikedTracksViewModel by viewModel()
    private lateinit var viewBinding: LikedTracksFragmentBinding

    companion object {
        fun newInstance() = LikedTracksFragment().apply {
//            arguments = Bundle().apply {
//                putInt(NUMBER, number)
//            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = LikedTracksFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.likedTracksLiveData.observe(viewLifecycleOwner) { playlist ->
            if (playlist.isEmpty()) {
                viewBinding.emptyImageView.visibility = View.VISIBLE
                viewBinding.emptyListText.visibility = View.VISIBLE
            }
        }
    }
}
