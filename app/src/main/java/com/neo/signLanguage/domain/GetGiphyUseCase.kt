package com.neo.signLanguage.domain

import com.neo.signLanguage.data.GiphyProvider
import com.neo.signLanguage.data.GiphyRepository
import com.neo.signLanguage.data.models.GiphyResponse

class GetGiphyUseCase {
    private val repository = GiphyRepository()
    suspend operator fun invoke(query: String) = repository.getGiphyByQuery(query)


}