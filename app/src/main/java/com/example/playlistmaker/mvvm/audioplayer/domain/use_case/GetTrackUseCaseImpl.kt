package com.example.playlistmaker.mvvm.audioplayer.domain.use_case

import com.example.playlistmaker.mvvm.search.domain.models.Track

interface GetTrackUseCaseImpl {

    suspend fun execute(): Track
}
