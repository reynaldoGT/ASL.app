package com.neo.signLanguage.data.network

import com.neo.signLanguage.core.RetrofitHelper
import com.neo.signLanguage.data.models.GiphyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GiphyService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getGiphy(query: String): List<GiphyItem> {
        return withContext(Dispatchers.IO) {
            val response =
                retrofit.create(GiphyApiClient::class.java).getGiphyImageByQuery(
                    "Fx16au8XaXm3cKQb9QsK2RoOR7rZL7G9", query, 20
                )
            response.body()?.data ?: emptyList()
        }
    }
}