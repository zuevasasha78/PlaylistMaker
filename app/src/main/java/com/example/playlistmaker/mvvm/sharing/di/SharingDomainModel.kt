package com.example.playlistmaker.mvvm.sharing.di

import com.example.playlistmaker.mvvm.sharing.domain.SharingUseCase
import com.example.playlistmaker.mvvm.sharing.domain.impl.SharingUseCaseImpl
import org.koin.dsl.module

val sharingDomainModule = module {

    single<SharingUseCase> {
        SharingUseCaseImpl(get())
    }
}