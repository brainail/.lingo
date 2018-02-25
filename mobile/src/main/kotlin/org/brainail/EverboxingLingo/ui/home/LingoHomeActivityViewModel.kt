package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.ViewModeSavedState
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : SearchViewModel() {

    enum class NavigationItem {
        BACKWARD
    }

    enum class NavigationTabItem {
        EXPLORE, FAVOURITE, HISTORY
    }

    private val navigation = SingleEventLiveData<NavigationItem>()
    private val navigationTab = SingleEventLiveData<NavigationTabItem>()

    fun navigation(): LiveData<NavigationItem> = navigation
    fun navigationTab(): LiveData<NavigationTabItem> = navigationTab
    fun searchViewState(): LiveData<SearchViewState> = searchViewState
    fun searchNavigation(): LiveData<SearchNavigationItem> = searchNavigation

    override fun initState(viewModelSavedState: ViewModeSavedState?) {
        super.initState(viewModelSavedState)

        // restore navigation
        val savedNavigation = viewModelSavedState?.get<String>(KEY_NAVIGATION_TAB_STATE) ?: NavigationTabItem.EXPLORE.name
        navigationTab.value = NavigationTabItem.valueOf(savedNavigation)
    }

    override fun saveState(): ViewModeSavedState {
        val saveState = super.saveState()
        navigationTab.value?.let { saveState.put(KEY_NAVIGATION_TAB_STATE, it.name) }
        return saveState
    }

    fun navigateTabTo(navigationTabItem: NavigationTabItem) {
        if (searchViewState.value!!.isInFocus) {
            if (navigation.value != navigationTabItem) {
                requestFocusGain(false)
            }
        }

        navigationTab.value = navigationTabItem
    }

    fun navigateTo(navigationItem: NavigationItem) {
        if (searchViewState.value!!.isInFocus) {
            if (NavigationItem.BACKWARD == navigationItem) {
                requestFocusGain(false)
                return
            }
        }

        navigation.value = navigationItem
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

    private companion object {
        const val KEY_NAVIGATION_TAB_STATE = "navigation_tab_state"
    }

}