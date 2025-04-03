package com.example.playlistmaker.new.sharing.domain

import com.example.playlistmaker.new.sharing.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun openLink(string: String)
    fun openEmail(emailData: EmailData)
}
