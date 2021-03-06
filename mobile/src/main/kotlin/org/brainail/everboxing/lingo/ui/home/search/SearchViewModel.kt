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

package org.brainail.everboxing.lingo.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange
import org.brainail.everboxing.lingo.ui.base.RxAwareViewModel
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.ClearIconClicked
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.NavigationIconClickedInFocus
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.RequestFocusGain
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.SubmitQuery
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.SuggestionsPrepared
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.SuggestionsStartedLoading
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.TextToSpeechResultSuccess
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.UpdateQuery
import org.brainail.everboxing.lingo.util.SingleEventLiveData
import org.brainail.everboxing.lingo.util.TextToSpeechResult

abstract class SearchViewModel : RxAwareViewModel() {
    enum class SearchNavigationItem { DRAWER, TEXT_TO_SPEECH }

    protected val searchViewState = MutableLiveData<SearchViewState>()
    private val searchSuggestions = MutableLiveData<String>()
    private val searchResults = MutableLiveData<SuggestionModel>()
    protected val searchNavigation = SingleEventLiveData<SearchNavigationItem>()

    init {
        searchViewState.value = SearchViewState.INITIAL
    }

    fun searchSuggestions(): LiveData<String> = searchSuggestions
    fun searchResults(): LiveData<SuggestionModel> = searchResults

    fun suggestionsStartedLoading() {
        applyChanges(SuggestionsStartedLoading)
    }

    fun suggestionsPrepared(suggestions: List<SuggestionModel>) {
        applyChanges(SuggestionsPrepared(suggestions))
    }

    fun updateQuery(query: String) {
        applyChanges(UpdateQuery(query)).takeIf { it.isInFocus }?.run {
            searchSuggestions.value = query.trim()
        }
    }

    fun submitQuery(query: String) {
        applyChanges(SubmitQuery(query.trim()))
        searchResults.value = SuggestionModel(query.trim())
    }

    fun submitQuery(suggestion: SuggestionModel) {
        applyChanges(SubmitQuery(suggestion.word.toString()))
        searchResults.value = suggestion
    }

    protected fun submitQuerySilently(query: String) {
        searchResults.value = SuggestionModel(query.trim(), isSilent = true)
    }

    fun requestFocusGain(isInFocus: Boolean) {
        applyChanges(RequestFocusGain(isInFocus)).takeIf { it.isInFocus }?.run {
            searchSuggestions.value = displayedText
        }
    }

    fun navigationIconClicked() {
        when (searchViewState.value!!.isInFocus) {
            true -> applyChanges(NavigationIconClickedInFocus)
            else -> searchNavigation.value = SearchViewModel.SearchNavigationItem.DRAWER
        }
    }

    fun clearIconClicked() {
        applyChanges(ClearIconClicked)
    }

    fun textToSpeechIconClicked() {
        searchNavigation.value = SearchViewModel.SearchNavigationItem.TEXT_TO_SPEECH
    }

    fun handleTextToSpeechResult(result: TextToSpeechResult) {
        when (result) {
            is TextToSpeechResult.Successful -> applyChanges(TextToSpeechResultSuccess(result))
        }
    }

    protected fun applyChanges(partialViewStateChange: PartialViewStateChange<SearchViewState>): SearchViewState {
        searchViewState.value = partialViewStateChange.applyTo(searchViewState.value!!)
        return searchViewState.value!!
    }
}
