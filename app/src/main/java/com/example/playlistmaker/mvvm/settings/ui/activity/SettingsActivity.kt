package com.example.playlistmaker.mvvm.settings.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.mvvm.settings.ui.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsViewModel by viewModel()
    private lateinit var viewBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initializeScreenButtons()

        viewModel.getThemeSettings().observe(this) { isDarkTheme ->
            viewBinding.darkTheme.isChecked = isDarkTheme
        }
        viewBinding.darkTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateThemeSetting(isChecked)
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.settings) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeScreenButtons() {
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
        viewBinding.shareApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, viewModel.getShareApp())
            }
            startIntent(Intent.createChooser(shareIntent, getString(R.string.share_app)))
        }
        viewBinding.legalAgreement.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(viewModel.getLegalAgreement())
            }
            startIntent(intent)
        }
        viewBinding.callSupport.setOnClickListener {
            val supportData = viewModel.getSupportData()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, supportData.email)
                putExtra(Intent.EXTRA_SUBJECT, supportData.subject)
                putExtra(Intent.EXTRA_TEXT, supportData.text)
            }
            startIntent(intent)
        }
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
