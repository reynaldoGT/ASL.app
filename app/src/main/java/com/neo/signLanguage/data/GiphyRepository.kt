package com.neo.signLanguage.data

import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.data.network.GiphyService
import com.orhanobut.logger.Logger
import javax.inject.Inject

class GiphyRepository {
  private val api = GiphyService()
  private val quoteProvider = GiphyProvider()
  suspend fun getGiphyByQuery(query: String): List<GiphyItem> {
    var response = emptyList<GiphyItem>()
    try {
      response = api.getGiphy(query)
      quoteProvider.giphys = response
    } catch (e: Exception) {
      Logger.e(e, "Error getting giphy")
    }
    return response
  }
}