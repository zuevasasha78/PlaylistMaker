package com.example.playlistmaker.mvvm.settings.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.mvvm.settings.ui.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModel()
    private var viewBinding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeScreenButtons()
        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkTheme ->
            viewBinding?.let { it.darkTheme.isChecked = isDarkTheme }
        }
        viewBinding?.let {
            it.darkTheme.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateThemeSetting(isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun initializeScreenButtons() {
        viewBinding?.let {
            it.shareApp.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, viewModel.getShareApp())
                }
                startIntent(Intent.createChooser(shareIntent, getString(R.string.share_app)))
            }
            it.legalAgreement.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = viewModel.getLegalAgreement().toUri()
                }
                startIntent(intent)
            }
            it.callSupport.setOnClickListener {
                val supportData = viewModel.getSupportData()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_EMAIL, supportData.email)
                    putExtra(Intent.EXTRA_SUBJECT, supportData.subject)
                    putExtra(Intent.EXTRA_TEXT, supportData.text)
                }
                startIntent(intent)
            }
        }
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }
}
