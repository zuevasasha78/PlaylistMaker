package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        val shareButton = findViewById<FrameLayout>(R.id.share_app)
        addShareButtonListener(shareButton)

        val callSupportButton = findViewById<FrameLayout>(R.id.call_support)
        addCallSupportListener(callSupportButton)

        val userAgreementButton = findViewById<FrameLayout>(R.id.legal_agreement)
        addUserAgreementListener(userAgreementButton)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addUserAgreementListener(userAgreementButton: FrameLayout) {
        userAgreementButton.setOnClickListener {
            val url = "https://yandex.ru/legal/practicum_offer/"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }

            startIntent(intent)
        }
    }

    private fun addCallSupportListener(callSupportButton: FrameLayout) {
        callSupportButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, "zuevasasha78@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам и разработчицам приложения Playlist Maker")
                putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!")
            }

            startIntent(intent)
        }
    }

    private fun addShareButtonListener(shareButton: FrameLayout) {
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/?from=catalog")
            }
            startIntent(Intent.createChooser(shareIntent, "Поделиться приложением"))
        }
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
