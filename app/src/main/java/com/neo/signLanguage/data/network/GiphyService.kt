package com.neo.signLanguage.data.network

import com.neo.signLanguage.data.models.GiphyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GiphyService @Inject constructor(
    private val api: GiphyApiClient
) {

    suspend fun getGiphy(query: String): List<GiphyItem> {
        return withContext(Dispatchers.IO) {
            val response =
                api.getGiphyImageByQuery("Fx16au8XaXm3cKQb9QsK2RoOR7rZL7G9", query, 20)
            response.body()?.data ?: emptyList()
        }
    }
}