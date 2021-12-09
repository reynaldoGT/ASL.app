package com.neo.signLanguage.data.network

import com.neo.signLanguage.core.RetrofitHelper
import com.neo.signLanguage.data.models.GiphyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiphyService {
    private val retrofit = RetrofitHelper().getRetrofit()
    suspend fun getGiphy(query: String): GiphyResponse? {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(GiphyApiClient::class.java)
                    .getGiphyImageByQuery("857854757557", query, 20)
            response.body()
        }
    }
}