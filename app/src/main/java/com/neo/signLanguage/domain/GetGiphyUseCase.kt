package com.neo.signLanguage.domain


import com.neo.signLanguage.data.GiphyRepository
import javax.inject.Inject


class GetGiphyUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(query: String) = repository.getGiphyByQuery(query)
}

