package com.example.playlistmaker.mvvm.library.ui.fragment.createrplaylist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.example.playlistmaker.mvvm.library.domain.models.Playlist
import com.example.playlistmaker.mvvm.library.ui.view_model.createrplaylist.CreatePlaylistViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class CreatePlaylistFragment : Fragment() {

    private val viewModel: CreatePlaylistViewModel by viewModel()
    private var _viewBinding: FragmentCreatePlaylistBinding? = null
    private val viewBinding: FragmentCreatePlaylistBinding get() = _viewBinding!!
    private val glide: RequestManager by inject()

    private val colorBlue = R.color.blue
    private val colorGray = R.color.gray
    private var nameText: String? = null
    private var descriptionText: String? = null
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setPickMedia()
        setNameInputListener()
        setDescriptionInputListener()
        setBackListener()
        setCreateButtonListener()
    }

    private fun setBackListener() {
        viewBinding.toolbar.setNavigationOnClickListener {
            showDialogCondition()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showDialogCondition()
                }
            }
        )
    }

    private fun showDialogCondition() {
        if (!imageUrl.isNullOrEmpty()
            || !nameText.isNullOrEmpty()
            || !descriptionText.isNullOrEmpty()
        ) {
            showDialog()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.new_playlist_dialog_title)
            .setMessage(R.string.new_playlist_dialog_message)
            .setNegativeButton(R.string.new_playlist_dialog_cancel) { dialog, which ->
                findNavController().navigateUp()
            }
            .setPositiveButton(R.string.new_playlist_dialog_finish) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setCreateButtonListener() {
        viewBinding.createButton.setOnClickListener {
            if (!nameText.isNullOrEmpty()) {
                viewModel.createPlaylist(
                    Playlist(
                        name = nameText!!,
                        description = descriptionText,
                        coverImageUrl = imageUrl,
                        tracksList = emptyList(),
                        tracksAmount = 0,
                    )
                )
                val message = getString(R.string.new_playlist_toast, nameText)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun setPickMedia() {
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    setImage(uri)
                    saveImageToPrivateStorage(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        viewBinding.placeholderCover.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    private fun setNameInputListener() {
        viewBinding.nameInput.addTextChangedListener(
            onTextChanged = { text, start, before, count ->
                val shouldEnable = text?.toString()?.trimEnd()?.isNotEmpty() ?: false
                val color = if (shouldEnable) colorBlue else colorGray
                setCreateButtonColor(color)
            },
            afterTextChanged = { editable ->
                val trimmedText = editable.toString().trimEnd()
                nameText = trimmedText
            })
    }

    private fun setCreateButtonColor(color: Int) {
        viewBinding.createButton.backgroundTintList = ContextCompat
            .getColorStateList(requireContext(), color)
    }

    private fun setDescriptionInputListener() {
        viewBinding.nameInput.addTextChangedListener(
            afterTextChanged = { editable ->
                val trimmedText = editable.toString().trimEnd()
                descriptionText = trimmedText
            })
    }

    private fun saveImageToPrivateStorage(uri: Uri) {
        val filePath = File(
            requireContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES), "cover_album"
        )
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val file = File(filePath, "image_${System.currentTimeMillis()}.jpg")
        val inputStream = requireContext().contentResolver.openInputStream(uri)

        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        imageUrl = file.path
    }

    private fun setImage(uri: Uri) {
        viewBinding.placeholderCover.isVisible = false
        viewBinding.coverImage.isVisible = true

        val roundValue = 8
        val cornerRadius = roundValue * (resources.displayMetrics.density).toInt()

        glide.load(uri)
            .placeholder(R.drawable.placeholder)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .into(viewBinding.coverImage)
    }
}
