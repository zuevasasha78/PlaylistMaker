package com.example.playlistmaker.new.sharing.domain.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.R
import com.example.playlistmaker.new.sharing.domain.ExternalNavigator
import com.example.playlistmaker.new.sharing.domain.model.EmailData

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun shareLink(link: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.practicum_link))
        }
        startIntent(Intent.createChooser(shareIntent, context.getString(R.string.share_app)))
    }

    override fun openLink(string: String) {
        val url = context.getString(R.string.practicum_offer)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startIntent(intent)
    }

    override fun openEmail(emailData: EmailData) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(
                Intent.EXTRA_EMAIL,
                emailData.email
            )//arrayOf(context.getString(R.string.email_address)))
            putExtra(
                Intent.EXTRA_SUBJECT,
                emailData.subject
            )//context.getString(R.string.email_subtitle))
            putExtra(Intent.EXTRA_TEXT, emailData.text)//context.getString(R.string.email_text))
        }
        startIntent(intent)
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}
