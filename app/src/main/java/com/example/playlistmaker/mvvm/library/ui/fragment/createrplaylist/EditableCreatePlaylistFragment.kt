package com.example.playlistmaker.mvvm.library.ui.fragment.createrplaylist

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.playlistmaker.R
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.library.ui.view_model.createrplaylist.EditableCreatePlaylistViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditableCreatePlaylistFragment : CreatePlaylistFragment() {

    private val args: EditableCreatePlaylistFragmentArgs by navArgs()

    private val playlist: Playlist by lazy {
        Gson().fromJson(args.playlistData, Playlist::class.java)
    }

    override val viewModel: EditableCreatePlaylistViewModel by viewModel {
        parametersOf(playlist)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        setPickMedia()
        setNameInputListener()
        setDescriptionInputListener()
        setBackListener()
        setCreateButtonListener()
    }

    private fun initData() {
        viewBinding.createButton.text = getString(R.string.save)
        viewBinding.nameInput.setText(playlist.name)
        viewBinding.descriptionInput.setText(playlist.description)
        setImage(playlist.coverImageUrl)
        setCreateButtonColor(colorBlue)

        nameText = playlist.name
        descriptionText = playlist.description
        imageUrl = playlist.coverImageUrl
    }

    override fun setBackListener() {
        viewBinding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun setCreateButtonListener() {
        viewBinding.createButton.setOnClickListener {
            if (!nameText.isNullOrEmpty()) {
                val playlist = playlist.copy(
                    name = nameText!!,
                    description = descriptionText,
                    coverImageUrl = imageUrl,
                )
                viewModel.updatePlaylist(playlist)
                findNavController().navigateUp()
            }
        }
    }

}
