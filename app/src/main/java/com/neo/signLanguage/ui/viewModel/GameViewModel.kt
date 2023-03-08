package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Game


class GameViewModel : ViewModel() {

  val randomGameLetters = MutableLiveData<Game>()
  val sendMessageImages = MutableLiveData<ArrayList<Sign>>()

  val gridNumbersMessage = MutableLiveData<Int>().apply {
    value = 5
  }

  fun getRandomToFindLetter(amount: Int) {
    randomGameLetters.postValue(DataSign.getRandomToFindCorrectLetter(amount))
  }

  fun setMessageWithImages(singList: ArrayList<Sign>) {
    sendMessageImages.postValue(singList)
  }

  fun setGridNumbersMessage(intentNumber: Int) {
    gridNumbersMessage.postValue(intentNumber)
  }
}