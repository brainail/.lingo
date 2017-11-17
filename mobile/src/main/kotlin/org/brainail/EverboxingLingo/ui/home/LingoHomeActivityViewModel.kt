package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.BaseViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : BaseViewModel() {

    enum class NavigationItem {
        EXPLORE, FAVOURITE, HISTORY, BACKWARD
    }

    private val navigationLiveData = SingleEventLiveData<NavigationItem>()
    val navigation: LiveData<NavigationItem>
        get() = navigationLiveData

    init {
        navigationLiveData.value = NavigationItem.EXPLORE
    }

    fun navigateTo(navigationItem: NavigationItem) {
        navigationLiveData.value = navigationItem
    }

    fun handleTextToSpeechResult(result: TextToSpeechResult) {
        if (result is TextToSpeechResult.TextToSpeechSuccessfulResult) {

        }
    }

}