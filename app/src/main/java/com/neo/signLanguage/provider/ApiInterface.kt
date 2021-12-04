package com.neo.signLanguage.provider

import com.neo.signLanguage.models.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search")
    suspend fun getGiphyImage(
        @Query("api_key") apiKey: String,
        @Query("q") queryToFind: String,
        @Query("limit") limit: Int,
        ): Response<GiphyResponse>
}