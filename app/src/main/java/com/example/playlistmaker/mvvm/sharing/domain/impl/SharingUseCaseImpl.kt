package com.example.playlistmaker.mvvm.sharing.domain.impl

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.mvvm.sharing.domain.SharingUseCase
import com.example.playlistmaker.mvvm.sharing.domain.model.EmailData

class SharingUseCaseImpl(private val applicationContext: Context) : SharingUseCase {

    override fun getShareLink(): String = applicationContext.getString(R.string.practicum_link)

    override fun getLegalAgreement(): String = applicationContext.getString(R.string.practicum_offer)

    override fun getSupportData(): EmailData = EmailData(
        email = arrayOf(R.string.email_address),
        subject = R.string.email_subtitle,
        text = R.string.email_text
    )
}
