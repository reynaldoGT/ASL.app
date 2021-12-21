package com.neo.signLanguage.data

import com.neo.signLanguage.data.models.GiphyItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyProvider @Inject constructor() {
    var giphys: List<GiphyItem> = emptyList()
}