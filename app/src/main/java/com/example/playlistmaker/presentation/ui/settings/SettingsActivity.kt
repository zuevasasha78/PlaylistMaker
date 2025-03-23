package com.example.playlistmaker.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.App
import com.example.playlistmaker.App.Companion.DARK_THEME_KEY
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val toolbar = viewBinding.toolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }

        addShareButtonListener()
        addCallSupportListener()
        addUserAgreementListener()

        val app = applicationContext as App
        val darkTheme = viewBinding.darkTheme
        darkTheme.isChecked = app.isDarkTheme
        darkTheme.setOnCheckedChangeListener { switcher, checked ->
            saveThemeToPref(checked, app)
            app.switchTheme(checked)
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.settings) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveThemeToPref(checked: Boolean, app: App) {
        app.sharedPrefs.edit().putBoolean(DARK_THEME_KEY, checked).apply()
    }

    private fun addUserAgreementListener() {
        viewBinding.legalAgreement.setOnClickListener {
            val url = getString(R.string.practicum_offer)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startIntent(intent)
        }
    }

    private fun addCallSupportListener() {
        viewBinding.callSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subtitle))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text))
            }
            startIntent(intent)
        }
    }

    private fun addShareButtonListener() {
        viewBinding.shareApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.practicum_link))
            }
            startIntent(Intent.createChooser(shareIntent, getString(R.string.share_app)))
        }
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
