package org.brainail.EverboxingLingo.ui.home.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.base.PartialViewStateChange
import org.brainail.EverboxingLingo.ui.base.RxAwareViewModel
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.ClearIconClicked
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.NavigationIconClickedInFocus
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.RequestFocusGain
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.SubmitQuery
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.SuggestionsPrepared
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.SuggestionsStartedLoading
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.TextToSpeechResultSuccess
import org.brainail.EverboxingLingo.ui.home.search.SearchViewState.UpdateQuery
import org.brainail.EverboxingLingo.util.SingleEventLiveData

abstract class SearchViewModel : RxAwareViewModel() {

    enum class SearchNavigationItem {
        DRAWER, TEXT_TO_SPEECH
    }

    protected val searchViewState = MutableLiveData<SearchViewState>()
    protected val searchNavigation = SingleEventLiveData<SearchNavigationItem>()
    private val searchSuggestions = SingleEventLiveData<String>()
    private val searchResults = SingleEventLiveData<SuggestionModel>()

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
        applyChanges(SubmitQuery(query))
        searchResults.value = SuggestionModel(query)
    }

    fun submitQuery(suggestion: SuggestionModel) {
        applyChanges(SubmitQuery(suggestion.word.toString()))
        searchResults.value = suggestion
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

    private fun applyChanges(partialViewStateChange: PartialViewStateChange<SearchViewState>): SearchViewState {
        searchViewState.value = partialViewStateChange.applyTo(searchViewState.value!!)
        return searchViewState.value!!
    }
}