package com.example.playlistmaker.new.search.data.network

import com.example.playlistmaker.new.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}