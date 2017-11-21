package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : SearchViewModel() {

    enum class NavigationItem {
        EXPLORE, FAVOURITE, HISTORY, BACKWARD
    }

    private val _navigation = SingleEventLiveData<NavigationItem>()
    val navigation: LiveData<NavigationItem>
        get() = _navigation

    val searchViewState: LiveData<SearchViewState>
        get() = _searchViewState
    val searchNavigation: LiveData<SearchNavigationItem>
        get() = _searchNavigation

    init {
        _navigation.value = NavigationItem.EXPLORE
    }

    fun navigateTo(navigationItem: NavigationItem) {
        if (searchViewState.value!!.isInFocus) {
            if (navigation.value != navigationItem) {
                requestFocusGain(false)
            }
            if (NavigationItem.BACKWARD == navigationItem) {
                return // early exit, only backward navigation can be cancelled by search
            }
        }

        _navigation.value = navigationItem
    }

    // region search view model proxy
    fun updateQuery(query: String) {
        updateQueryInternally(query)
    }

    fun submitQuery(query: String) {
        submitQueryInternally(query)
    }

    fun requestFocusGain(isInFocus: Boolean) {
        requestFocusGainInternally(isInFocus)
    }

    fun navigationIconClicked() {
        navigationIconClickedInternally()
    }

    fun clearIconClicked() {
        clearIconClickedInternally()
    }

    fun textToSpeechIconClicked() {
        textToSpeechIconClickedInternally()
    }

    fun handleTextToSpeechResult(result: TextToSpeechResult) {
        handleTextToSpeechResultInternally(result)
    }
    // endregion

}