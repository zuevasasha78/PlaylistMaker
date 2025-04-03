package com.example.playlistmaker.new.sharing.domain.impl

import com.example.playlistmaker.new.sharing.domain.ExternalNavigator
import com.example.playlistmaker.new.sharing.domain.SharingInteractor
import com.example.playlistmaker.new.sharing.domain.model.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        //todo "// Нужно реализовать"
        return "// Нужно реализовать"
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(arrayOf(""), "", "")
    }

    private fun getTermsLink(): String {
        //todo // Нужно реализовать
        return "// Нужно реализовать"
    }
}
