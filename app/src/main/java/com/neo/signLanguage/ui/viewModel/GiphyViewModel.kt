package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.signLanguage.data.models.GiphyResponse
import com.neo.signLanguage.domain.GetGiphyUseCase
import kotlinx.coroutines.launch

class GiphyViewModel : ViewModel() {
    val giphyModel = MutableLiveData<GiphyResponse>()
    private val isLoading = MutableLiveData<Boolean>()
    var getGiphyUseCase = GetGiphyUseCase()

    fun getGiphys(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getGiphyUseCase(query)
            if (result != null) {
                giphyModel.postValue((result))
                isLoading.postValue(false)
            }
        }
    }

    /*fun getGiphys(query: String) {
        isLoading.postValue(true)
        val giphys = getGiphyUseCase()
        if (giphys != null) {
            giphyModel.postValue(giphys!!)
        }
        isLoading.postValue(false)

    }*/
}