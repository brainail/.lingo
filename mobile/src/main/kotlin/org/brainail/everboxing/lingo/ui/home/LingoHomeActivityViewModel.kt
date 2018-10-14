package org.brainail.everboxing.lingo.ui.home

import androidx.lifecycle.LiveData
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.ViewModelSavedState
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState
import org.brainail.everboxing.lingo.util.SingleEventLiveData
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : SearchViewModel() {

    enum class NavigationItem {
        BACKWARD, SCROLL_TO_TOP
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

    override fun initState(viewModelSavedState: ViewModelSavedState?) {
        super.initState(viewModelSavedState)

        initNavigationState(viewModelSavedState)
        initDisplayedTextState(viewModelSavedState)
    }

    private fun initNavigationState(viewModelSavedState: ViewModelSavedState?) {
        val savedNavigation = viewModelSavedState
                ?.get<String>(KEY_NAVIGATION_TAB_STATE) ?: NavigationTabItem.EXPLORE.name
        navigationTab.value = NavigationTabItem.valueOf(savedNavigation)
    }

    private fun initDisplayedTextState(viewModelSavedState: ViewModelSavedState?) {
        viewModelSavedState?.also {
            it.takeIf { isFirstRestore() }?.run {
                val displayedText = get<String>(KEY_DISPLAYED_TEXT_STATE) ?: ""
                updateQuery(displayedText)
                submitQuerySilently(displayedText)
            }
        } ?: run {
            submitQuerySilently("")
        }
    }

    override fun saveState(): ViewModelSavedState {
        val saveState = super.saveState()
        navigationTab.value?.let { saveState.put(KEY_NAVIGATION_TAB_STATE, it.name) }
        searchViewState.value?.let { saveState.put(KEY_DISPLAYED_TEXT_STATE, it.displayedText) }
        return saveState
    }

    fun navigateTabTo(navigationTabItem: NavigationTabItem) {
        if (searchViewState.value!!.isInFocus) {
            requestFocusGain(false)
        } else if (navigationTab.value == navigationTabItem) {
            navigation.value = NavigationItem.SCROLL_TO_TOP
            return
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

    fun suggestionClicked(suggestion: SuggestionModel) {
        submitQuery(suggestion.word.toString())
    }

    private companion object {
        const val KEY_NAVIGATION_TAB_STATE = "navigation_tab_state"
        const val KEY_DISPLAYED_TEXT_STATE = "displayed_text_state"
    }
}