package com.neo.signLanguage.data.network
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.data.models.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiClient {
    @GET("search")
    suspend fun getGiphyImageByQuery(
        @Query("api_key") apiKey: String,
        @Query("q") queryToFind: String,
        @Query("limit") limit: Int,
    ): Response<GiphyResponse>

}