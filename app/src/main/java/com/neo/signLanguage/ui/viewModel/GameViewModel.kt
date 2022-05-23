package com.neo.signLanguage.ui.viewModel

import androidx.lifecycle.*
import com.neo.signLanguage.data.database.entities.SignEntity
import com.neo.signLanguage.data.database.entities.dao.SignDao
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.data.models.SingDbModel
import com.neo.signLanguage.data.models.toDomain
import com.neo.signLanguage.domain.GetGiphyUseCase
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Game
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {

    val randomGameLetters = MutableLiveData<Game>()
    val amount = MutableLiveData<Int>().apply {
        value = 3
    }

    fun setCurrentMessage(amount: Int) {
        randomGameLetters.postValue(DataSign.getRandomToFindEquals(amount))
    }

    fun setIntents(intentNumber: Int) {
        amount.value?.let { a ->
            amount.value = a + intentNumber
        }
    }


}