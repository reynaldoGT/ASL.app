package com.neo.signLanguage.domain


import com.neo.signLanguage.data.GiphyRepository

class GetGiphyUseCase {
    private val repository = GiphyRepository()
    suspend operator fun invoke(query: String) = repository.getGiphyByQuery(query)
}

