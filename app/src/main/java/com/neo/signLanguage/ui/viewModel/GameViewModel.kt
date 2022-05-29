package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Game


class GameViewModel : ViewModel() {

    val randomGameLetters = MutableLiveData<Game>()
    val sendMessageImages = MutableLiveData<ArrayList<Sign>>()
    val intents = MutableLiveData<Int>().apply {
        value = 3
    }

    fun setCurrentMessage(amount: Int) {
        randomGameLetters.postValue(DataSign.getRandomLetters(amount))
    }

    fun setMessageWithImages(singList: ArrayList<Sign>) {
        sendMessageImages.postValue(singList)
    }

    fun setIntents(intentNumber: Int) {
        intents.value?.let { a ->
            intents.value = a + intentNumber
        }
    }


}