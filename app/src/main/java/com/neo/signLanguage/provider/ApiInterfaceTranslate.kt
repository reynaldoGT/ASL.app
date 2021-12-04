package com.neo.signLanguage.provider

import com.neo.signLanguage.models.TranslateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaceTranslate {
    @GET("get")
    suspend fun getWordTranslated(
        @Query("q") queryToTranslate: String,
        @Query("langpair") lang: String,
        ): Response<TranslateResponse>
}