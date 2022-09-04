package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neo.signLanguage.data.models.Sign

class PairGameViewModel : ViewModel() {
  private val _elements = MutableLiveData<List<Sign>>()
  var element: LiveData<List<Sign>> = _elements

  private var _matches = MutableLiveData<Int>()
  var matches: LiveData<Int> = _matches

  private var card1 = MutableLiveData<Sign?>()
  var card1LiveData: LiveData<Sign?> = card1

  private var card2 = MutableLiveData<Sign?>()
  var card2LiveData: LiveData<Sign?> = card2

  private var failure = MutableLiveData<Boolean>()
  var failureLiveData: LiveData<Boolean> = failure

  private var toggleFlip = MutableLiveData<Boolean>()
  var toggleFlipLiveData: LiveData<Boolean> = toggleFlip

  fun checkMatch() {
    if (this.card1 === this.card2) {

      /*matches = [...this.state.matches, this.state.card1],*/
      //matches
      _matches.value = _matches.value?.plus(1)
      /*updating one element of state*/

      card1.value = null
      card2.value = null

    } else {
      card1.value = null
      card2.value = null
      toggleFlip.value = !toggleFlip

    }
  };
}
}