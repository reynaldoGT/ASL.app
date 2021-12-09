package com.neo.signLanguage.data

import com.neo.signLanguage.data.models.GiphyResponse
import com.neo.signLanguage.data.network.GiphyService

class GiphyRepository {
    private val api = GiphyService()
    suspend fun getGiphyByQuery(query: String): GiphyResponse{
        val response = api.getGiphy(query)
        GiphyProvider.giphys = response
        return response?
    }
}