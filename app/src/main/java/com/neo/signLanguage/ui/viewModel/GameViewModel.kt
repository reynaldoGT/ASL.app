package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Game


class GameViewModel : ViewModel() {

    val randomGameLetters = MutableLiveData<Game>()
    val sendMessageImages = MutableLiveData<ArrayList<Sign>>()
    val intents = MutableLiveData<Int>().apply {
        value = 0
    }
    val gridNumbersMessage = MutableLiveData<Int>().apply {
        value = 5
    }

    fun getRandomToFindLetter(amount: Int) {
        randomGameLetters.postValue(DataSign.getRandomToFindCorrectLetter(amount))
    }
    fun getRandomToFindEquals(amount: Int) {
        randomGameLetters.postValue(DataSign.getRandomToFindEquals(amount))
    }

    fun setMessageWithImages(singList: ArrayList<Sign>) {
        sendMessageImages.postValue(singList)
    }

    fun setIntents(intentNumber: Int) {
        intents.value?.let { a ->
            intents.value = a + intentNumber
        }
    }
    fun setGridNumbersMessage(intentNumber: Int) {
        gridNumbersMessage.postValue(intentNumber)
    }


}