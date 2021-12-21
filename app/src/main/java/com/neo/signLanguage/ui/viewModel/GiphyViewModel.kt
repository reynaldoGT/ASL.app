package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.domain.GetGiphyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val getGiphyUseCase: GetGiphyUseCase,
) : ViewModel() {

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