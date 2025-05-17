package com.example.playlistmaker.mvvm.library.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.PlaylistFragmentBinding

class PlaylistFragment : Fragment() {

    private lateinit var viewBinding: PlaylistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = PlaylistFragmentBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //todo выровнять toolbar
        viewBinding.toolbar.setNavigationOnClickListener {
            activity?.finish()
        }
    }
}
