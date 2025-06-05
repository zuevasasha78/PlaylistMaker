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

    private var viewBinding: FragmentLibraryBinding? = null
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLibraryBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding?.let {
            it.viewPager.adapter = PagerAdapter(childFragmentManager, lifecycle)

            tabMediator =
                TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                    when (position) {
                        0 -> tab.text = requireContext().getString(R.string.saved_tracks)
                        1 -> tab.text = requireContext().getString(R.string.playlists)
                    }
                }
            tabMediator.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
        viewBinding = null
    }
}
