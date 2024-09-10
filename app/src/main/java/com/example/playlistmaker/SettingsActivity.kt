package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val shareButton = findViewById<TextView>(R.id.share_app)
        addShareButtonListener(shareButton)

        val callSupportButton = findViewById<TextView>(R.id.call_support)
        addCallSupportListener(callSupportButton)

        val userAgreementButton = findViewById<TextView>(R.id.legal_agreement)
        addUserAgreementListener(userAgreementButton)

        val app = applicationContext as App
        val darkTheme = findViewById<SwitchCompat>(R.id.dark_theme)
        darkTheme.isChecked = app.isDarkTheme
        darkTheme.setOnCheckedChangeListener { switcher, checked ->
            saveThemeToPref(checked, app)
            app.switchTheme(checked)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveThemeToPref(checked: Boolean, app: App) {
        app.sharedPrefs.edit().putBoolean(DARK_THEME_KEY, checked).apply()
    }

    private fun addUserAgreementListener(view: View) {
        view.setOnClickListener {
            val url = getString(R.string.practicum_offer)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startIntent(intent)
        }
    }

    private fun addCallSupportListener(view: View) {
        view.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subtitle))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text))
            }
            startIntent(intent)
        }
    }

    private fun addShareButtonListener(view: View) {
        view.setOnClickListener {
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
