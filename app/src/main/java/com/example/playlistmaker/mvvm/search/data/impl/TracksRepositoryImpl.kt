package com.example.playlistmaker.mvvm.search.data.impl

import com.example.playlistmaker.mvvm.search.data.dto.TrackListResponse
import com.example.playlistmaker.mvvm.search.data.dto.TracksSearchRequest
import com.example.playlistmaker.mvvm.search.data.network.NetworkClient
import com.example.playlistmaker.mvvm.search.domain.api.SearchState
import com.example.playlistmaker.mvvm.search.domain.api.TracksRepository
import com.example.playlistmaker.mvvm.search.domain.models.Track
import com.example.playlistmaker.utils.convertMsToData
import com.example.playlistmaker.utils.convertStringToData

class TracksRepositoryImpl(val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): SearchState {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        if (response.resultCode == 200 && response is TrackListResponse) {
            val trackList = response.results

            if (!trackList.isNullOrEmpty()) {
                val trackData = trackList.map { dto ->
                    Track(
                        trackId = dto.trackId,
                        trackName = dto.trackName,
                        artistName = dto.artistName,
                        trackTimeMillis = convertMsToData(dto.trackTimeMillis, "mm:ss"),
                        artworkUrl100 = dto.artworkUrl100,
                        collectionName = dto.collectionName,
                        releaseDate = convertStringToData(dto.releaseDate, "yyyy"),
                        primaryGenreName = dto.primaryGenreName,
                        country = dto.country,
                        previewUrl = dto.previewUrl,
                    )
                }
                return SearchState.Success(trackData)
            } else {
                return SearchState.DataError("Ничего не нашлось")
            }
        } else {
            return SearchState.NetworkError(
                "Проблемы со связью\n" +
                    "\n" +
                    "Загрузка не удалась. Проверьте подключение к интернету"
            )
        }
    }
}
