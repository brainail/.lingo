/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.ui.home

import androidx.lifecycle.LiveData
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.ViewModelSavedState
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState
import org.brainail.everboxing.lingo.util.SharedViewModel
import org.brainail.everboxing.lingo.util.SingleEventLiveData
import org.brainail.logger.L
import javax.inject.Inject

@SharedViewModel(clazz = SearchViewModel::class)
class LingoHomeActivityViewModel @Inject constructor() : SearchViewModel() {
    enum class NavigationItem { BACKWARD, SCROLL_TO_TOP }
    enum class NavigationTabItem { EXPLORE, FAVOURITE, HISTORY }

    private val navigation = SingleEventLiveData<NavigationItem>()
    private val navigationTab = SingleEventLiveData<NavigationTabItem>()

    fun navigation(): LiveData<NavigationItem> = navigation
    fun navigationTab(): LiveData<NavigationTabItem> = navigationTab
    fun searchViewState(): LiveData<SearchViewState> = searchViewState
    fun searchNavigation(): LiveData<SearchNavigationItem> = searchNavigation

    override fun initState(viewModelSavedState: ViewModelSavedState?) {
        super.initState(viewModelSavedState)
        initDisplayedTextState(viewModelSavedState)
        initNavigationState(viewModelSavedState)
    }

    override fun saveState(): ViewModelSavedState {
        val saveState = super.saveState()
        navigationTab.value?.let { saveState.put(KEY_NAVIGATION_TAB_STATE, it.name) }
        searchViewState.value?.let { saveState.put(KEY_DISPLAYED_TEXT_STATE, it.displayedText) }
        return saveState
    }

    fun navigateTabTo(navigationTabItem: NavigationTabItem) {
        if (navigationTab.value == navigationTabItem) {
            navigation.value = NavigationItem.SCROLL_TO_TOP
            return
        } else if (searchViewState.value!!.isInFocus) {
            requestFocusGain(false)
        }

        navigationTab.value = navigationTabItem
        submitQuerySilently(searchViewState.value!!.displayedText) // simulate new creation
    }

    fun navigateTo(navigationItem: NavigationItem) {
        if (searchViewState.value!!.isInFocus && NavigationItem.BACKWARD == navigationItem) {
            requestFocusGain(false)
            return
        }

        navigation.value = navigationItem
    }

    fun actionButtonClicked() {
        val viewState = searchViewState.value!!
        when (viewState.isInFocus) {
            true -> submitQuery(viewState.displayedText)
            else -> applyChanges(SearchViewState.RequestFocusGain(true, SearchViewState.CursorPosition.END))
        }
    }

    fun suggestionClicked(suggestion: SuggestionModel) {
        submitQuery(suggestion.word.toString())
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

    /**
     * [androidx.navigation.NavController] restores state on it's own. Keep for awhile.
     */
    @Suppress("unused")
    private fun initNavigationState(viewModelSavedState: ViewModelSavedState?) {
        viewModelSavedState?.get<String>(KEY_NAVIGATION_TAB_STATE)?.also {
            L.v("initNavigationState(): restore navigation tab $it")
            navigationTab.setValueSilently(NavigationTabItem.valueOf(it))
        } ?: run {
            L.v("initNavigationState(): use default tab for fresh start")
            navigationTab.value = NavigationTabItem.EXPLORE
        }
    }

    private companion object {
        const val KEY_NAVIGATION_TAB_STATE = "navigation_tab_state"
        const val KEY_DISPLAYED_TEXT_STATE = "displayed_text_state"
    }
}
