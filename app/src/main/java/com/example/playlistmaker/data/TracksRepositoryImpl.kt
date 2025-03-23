package com.example.playlistmaker.data

import com.example.playlistmaker.convertMsToData
import com.example.playlistmaker.convertStringToData
import com.example.playlistmaker.data.dto.TrackListResponse
import com.example.playlistmaker.data.dto.TracksSearchRequest
import com.example.playlistmaker.domain.api.TracksData
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Track

class TracksRepositoryImpl(val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): TracksData<List<Track>> {
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
                return TracksData.Success(trackData)
            } else {
                return TracksData.DataError("Ничего не нашлось")
            }
        } else {
            return TracksData.NetworkError(
                "Проблемы со связью\n" +
                    "\n" +
                    "Загрузка не удалась. Проверьте подключение к интернету"
            )
        }
    }
}
