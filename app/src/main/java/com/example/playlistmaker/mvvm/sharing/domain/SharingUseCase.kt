package com.example.playlistmaker.mvvm.sharing.domain

import com.example.playlistmaker.mvvm.sharing.domain.model.EmailData

interface SharingUseCase {
    fun getShareLink(): String
    fun getLegalAgreement(): String
    fun getSupportData(): EmailData
}
