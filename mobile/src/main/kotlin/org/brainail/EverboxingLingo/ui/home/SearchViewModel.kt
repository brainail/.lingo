package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.brainail.EverboxingLingo.extensions.EMPTY_TEXT
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.BaseViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData

abstract class SearchViewModel : BaseViewModel() {

    enum class SearchNavigationItem {
        DRAWER, TEXT_TO_SPEECH
    }

    protected val _searchViewState = MutableLiveData<SearchViewState>()
    protected val _searchNavigation = SingleEventLiveData<SearchNavigationItem>()

    private val _searchForSuggestionsCall = SingleEventLiveData<String>()
    val searchForSuggestionsCall: LiveData<String>
        get() = _searchForSuggestionsCall

    private val _searchForResultsCall = SingleEventLiveData<String>()
    val searchForResultsCall: LiveData<String>
        get() = _searchForResultsCall

    init {
        _searchViewState.value = SearchViewState.INITIAL
    }

    protected fun updateQueryInternally(query: String) {
        val viewState = _searchViewState.value!!
        val shouldSearchForSuggestions = viewState.isInFocus
        _searchViewState.value = viewState.copy(
                displayedText = query,
                isClearAvailable = viewState.isInFocus && query.isNotEmpty(),
                isLogoDisplayed = !viewState.isInFocus && query.isEmpty(),
                isLoadingSuggestions = viewState.isInFocus,
                isTextToSpeechResult = false)
        if (shouldSearchForSuggestions) {
            _searchForSuggestionsCall.value = query.trim()
        }
    }

    protected fun submitQueryInternally(query: String) {
        _searchViewState.value = _searchViewState.value!!.copy(
                isInFocus = false,
                isTextToSpeechResult = false)
        _searchForResultsCall.value = query.trim()
    }

    protected fun requestFocusGainInternally(isInFocus: Boolean) {
        val viewState = _searchViewState.value!!
        _searchViewState.value = viewState.copy(
                isInFocus = isInFocus,
                isClearAvailable = isInFocus && viewState.displayedText.isNotEmpty(),
                isLogoDisplayed = !isInFocus && viewState.displayedText.isEmpty(),
                isLoadingSuggestions = if (!isInFocus) false else viewState.displayLoading,
                isTextToSpeechResult = false)
    }

    protected fun navigationIconClickedInternally() {
        val viewState = _searchViewState.value!!
        when (viewState.isInFocus) {
            true -> _searchViewState.value = viewState.copy(
                    isInFocus = false,
                    isTextToSpeechResult = false)
            else -> _searchNavigation.value = SearchNavigationItem.DRAWER
        }
    }

    protected fun clearIconClickedInternally() {
        _searchViewState.value = _searchViewState.value!!.copy(
                displayedText = EMPTY_TEXT,
                isTextToSpeechResult = false)
    }

    protected fun textToSpeechIconClickedInternally() {
        _searchNavigation.value = SearchNavigationItem.TEXT_TO_SPEECH
    }

    protected fun handleTextToSpeechResultInternally(result: TextToSpeechResult) {
        if (result is TextToSpeechResult.TextToSpeechSuccessfulResult) {
            _searchViewState.value = _searchViewState.value!!.copy(
                    isInFocus = true,
                    displayedText = result.text,
                    isTextToSpeechResult = true)
        }
    }

    fun updateSuggestions(suggestions: List<String>) {
        TODO("...")
    }

}