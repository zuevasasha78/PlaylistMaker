package com.example.playlistmaker.mvvm.search.data.network

import com.example.playlistmaker.mvvm.search.data.dto.TrackListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    @GET("search?entity=song")
    fun search(@Query("term") text: String): Call<TrackListResponse>
}