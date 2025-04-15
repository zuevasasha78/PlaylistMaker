package com.example.playlistmaker.mvvm.sharing.domain.di

import com.example.playlistmaker.mvvm.sharing.domain.SharingUseCase
import com.example.playlistmaker.mvvm.sharing.domain.impl.SharingUseCaseImpl
import org.koin.dsl.module

val sharingDomainModule = module {

    single<SharingUseCase> {
        SharingUseCaseImpl(get())
    }
}