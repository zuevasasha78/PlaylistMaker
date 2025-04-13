package com.example.playlistmaker.new.sharing.domain

import com.example.playlistmaker.new.sharing.domain.model.EmailData

interface SharingUseCase {
    fun getShareLink(): String
    fun getLegalAgreement(): String
    fun getSupportData(): EmailData
}
