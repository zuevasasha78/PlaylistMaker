package com.example.playlistmaker.mvvm.library.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LikedTracksFragment.newInstance()
            1 -> PlaylistListFragment.newInstance()
            else -> LikedTracksFragment.newInstance()
        }
    }

    override fun getItemCount(): Int = 2
}
