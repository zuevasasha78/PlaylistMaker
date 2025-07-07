package com.example.playlistmaker.mvvm.library.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : Fragment() {

    private val viewBinding: FragmentLibraryBinding get() = _viewBinding!!
    private var _viewBinding: FragmentLibraryBinding? = null
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentLibraryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.viewPager.adapter = PagerAdapter(childFragmentManager, lifecycle)

        tabMediator =
            TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = requireContext().getString(R.string.saved_tracks)
                    1 -> tab.text = requireContext().getString(R.string.playlists)
                }
            }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        tabMediator.detach()
        _viewBinding = null
        super.onDestroyView()
    }
}
