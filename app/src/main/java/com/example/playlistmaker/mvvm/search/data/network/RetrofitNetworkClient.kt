package com.example.playlistmaker.mvvm.search.data.network

import com.example.playlistmaker.mvvm.search.data.dto.Response
import com.example.playlistmaker.mvvm.search.data.dto.TracksSearchRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val BASE_URL = "https://itunes.apple.com/"

    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val iTunesService: ITunesService = retrofit.create(ITunesService::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is TracksSearchRequest) {
            val resp = iTunesService.search(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}