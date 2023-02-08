package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.data.models.SingDbModel
import com.neo.signLanguage.data.models.toDomain
import com.neo.signLanguage.domain.GetGiphyUseCase
import com.neo.signLanguage.ui.view.activities.MainActivity
import kotlinx.coroutines.launch


class GiphyViewModel : ViewModel() {

  val getGiphyUseCase = GetGiphyUseCase()
  val currentMessage = MutableLiveData<String>()
  val giphyModel = MutableLiveData<List<GiphyItem>>()
  val saveInDatabase = MutableLiveData<Boolean>()
  val getAllSingFromDatabase = MutableLiveData<List<SingDbModel>>()
  private val isLoading = MutableLiveData<Boolean>()

  fun setCurrentMessage(newCurrentMessage: String, saveDatabase: Boolean) {
    currentMessage.postValue(newCurrentMessage)
    saveInDatabase.postValue(saveDatabase)
  }

  fun getGiphys(query: String) {
    viewModelScope.launch {
      isLoading.postValue(true)
      val result = getGiphyUseCase(query)
      giphyModel.postValue(result)
      isLoading.postValue(false)
    }
  }

  fun getAllSingFromDatabase() {
    getAllSingFromDatabase.postValue(
      MainActivity.database.getSignDao().getAllSing().map { it.toDomain() })
  }

  fun deleteAllHistory() {
    MainActivity.database.getSignDao().deleteAllRaw()
    getAllSingFromDatabase.postValue(
      MainActivity.database.getSignDao().getAllSing().map { it.toDomain() })
  }

  fun deleteById(id: Long) {
    MainActivity.database.getSignDao().deleteSingById(id)
    getAllSingFromDatabase.postValue(
      MainActivity.database.getSignDao().getAllSing().map { it.toDomain() })
  }
}