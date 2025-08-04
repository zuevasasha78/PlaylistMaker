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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class CreatePlaylistFragment : Fragment() {

    private val viewModel: CreatePlaylistViewModel by viewModel()
    private var _viewBinding: FragmentCreatePlaylistBinding? = null
    private val viewBinding: FragmentCreatePlaylistBinding get() = _viewBinding!!
    private val glide: RequestManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }

    private fun setImage(uri: Uri) {
        viewBinding.placeholderCover.visibility = View.GONE
        viewBinding.coverImage.visibility = View.VISIBLE

        val roundValue = 8
        val cornerRadius = roundValue * (resources.displayMetrics.density).toInt()

        glide.load(uri)
            .placeholder(R.drawable.placeholder)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .into(viewBinding.coverImage)
    }
}
