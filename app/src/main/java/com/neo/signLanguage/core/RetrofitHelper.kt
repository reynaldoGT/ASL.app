package com.neo.signLanguage.core

import com.neo.signLanguage.data.network.ErrorHandlingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitHelper {
  companion object {
    fun getRetrofit(): Retrofit {
      return Retrofit.Builder()
        .baseUrl("https://api.giphy.com/v1/gifs/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(ErrorHandlingInterceptor()).build())
        .build()
    }
  }
}