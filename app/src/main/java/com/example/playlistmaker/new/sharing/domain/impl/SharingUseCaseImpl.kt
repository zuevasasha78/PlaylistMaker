package com.example.playlistmaker.new.sharing.domain.impl

import com.example.playlistmaker.R
import com.example.playlistmaker.new.sharing.domain.SharingUseCase
import com.example.playlistmaker.new.sharing.domain.model.EmailData

class SharingUseCaseImpl() : SharingUseCase {

    override fun getShareLink(): Int = R.string.practicum_link

    override fun getLegalAgreement(): Int = R.string.practicum_offer

    override fun getSupportData(): EmailData = EmailData(
        email = arrayOf(R.string.email_address),
        subject = R.string.email_subtitle,
        text = R.string.email_text
    )
}
