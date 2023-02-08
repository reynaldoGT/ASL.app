package com.neo.signLanguage.di

import com.neo.signLanguage.data.network.GiphyApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Singleton
  @Provides
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://api.giphy.com/v1/gifs/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  fun provideGiphyApiClient(retrofit: Retrofit): GiphyApiClient {
    return retrofit.create(GiphyApiClient::class.java)
  }
}