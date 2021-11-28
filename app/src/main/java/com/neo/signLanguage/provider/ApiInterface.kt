package com.neo.signLanguage.provider

import com.neo.signLanguage.models.GhiphyReponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("search")
    suspend fun getGiphyImage(
        @Query("api_key") apiKey: String,
        @Query("q") queryToFind: String
    ): Response<GhiphyReponse>
}