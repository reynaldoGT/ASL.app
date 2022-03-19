package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.database.entities.SignEntity
import com.neo.signLanguage.data.database.entities.dao.SignDao
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.data.models.SingDbModel
import com.neo.signLanguage.data.models.toDomain
import com.neo.signLanguage.domain.GetGiphyUseCase
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch


class GiphyViewModel : ViewModel() {

    val getGiphyUseCase = GetGiphyUseCase()
    val currentMessage = MutableLiveData<String>()
    val giphyModel = MutableLiveData<List<GiphyItem>>()
    val saveInDatabase = MutableLiveData<Boolean>()
    val getAllSingFromDatabase = MutableLiveData<List<SingDbModel>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun setCurrentMessage(newCurrentMessage: String, saveDatabase: Boolean) {
        Logger.d(newCurrentMessage)
        currentMessage.postValue(newCurrentMessage)
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
            TabNavigatorActivity.database.getSignDao().getAllSing().map { it.toDomain() })
    }
}