package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.domain.GetGiphyUseCase
import kotlinx.coroutines.launch


class GiphyViewModel : ViewModel() {

    val getGiphyUseCase = GetGiphyUseCase()
    val currentMessage = MutableLiveData<String>()
    val giphyModel = MutableLiveData<List<GiphyItem>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun setCurrentMessage(newCurrentMessage: String) {
        currentMessage.postValue(newCurrentMessage)
    }

    fun getGiphys(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getGiphyUseCase(query)
            giphyModel.postValue(result)
            isLoading.postValue(false)

        }
    }
}