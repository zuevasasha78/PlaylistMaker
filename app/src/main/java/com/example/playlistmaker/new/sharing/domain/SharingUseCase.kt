package com.example.playlistmaker.new.sharing.domain

import com.example.playlistmaker.new.sharing.domain.model.EmailData

interface SharingUseCase {
    fun getShareLink(): Int
    fun getLegalAgreement(): Int
    fun getSupportData(): EmailData
}
