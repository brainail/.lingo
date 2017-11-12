package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import org.brainail.EverboxingLingo.ui.BaseViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : BaseViewModel() {

    enum class NavigationItem {
        EXPLORE, FAVOURITE, HISTORY, BACKWARD
    }

    private val mutableNavLiveData = SingleEventLiveData<NavigationItem>()
    val navigation: LiveData<NavigationItem>
        get() = mutableNavLiveData

    init {
        mutableNavLiveData.value = NavigationItem.EXPLORE
    }

    fun navigateTo(navigationItem: NavigationItem) {
        mutableNavLiveData.value = navigationItem
    }

}