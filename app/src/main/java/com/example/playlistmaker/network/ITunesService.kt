package com.example.playlistmaker.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesService {

    @GET("search?entity=song")
    fun search(@Query("term") text: String)
}
