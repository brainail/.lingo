package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.brainail.EverboxingLingo.ui.BaseViewModel
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : BaseViewModel() {

    enum class NavigationItem {
        EXPLORE, FAVOURITE, HISTORY
    }

    private val mutableNavLiveData = MutableLiveData<NavigationItem>()
    val navigationLiveData: LiveData<NavigationItem>
        get() = mutableNavLiveData

    init {
        mutableNavLiveData.value = NavigationItem.EXPLORE
    }

    fun navigate(navigationItem: NavigationItem) {
        mutableNavLiveData.value = navigationItem
    }

}