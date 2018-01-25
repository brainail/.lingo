package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import org.brainail.EverboxingLingo.extensions.EMPTY_TEXT
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.RxAwareViewModel
import org.brainail.EverboxingLingo.ui.home.SearchViewState.CursorPosition
import org.brainail.EverboxingLingo.util.SingleEventLiveData


abstract class SearchViewModel : RxAwareViewModel() {

    enum class SearchNavigationItem {
        DRAWER, TEXT_TO_SPEECH
    }

    protected val searchViewState = MutableLiveData<SearchViewState>()
    protected val searchNavigation = SingleEventLiveData<SearchNavigationItem>()
    private val searchSuggestions = SingleEventLiveData<String>()
    private val searchResults = SingleEventLiveData<String>()

    init {
        searchViewState.value = SearchViewState.INITIAL
    }

    fun searchSuggestions(): LiveData<String> = searchSuggestions
    fun searchResults(): LiveData<String> = searchResults

    fun suggestionsPrepared(suggestions: List<String>) {
        searchViewState.value = searchViewState.value!!.copy(
                isLoadingSuggestions = false,
                displayedSuggestions = suggestions)
    }

    protected fun updateQueryInternally(query: String) {
        val viewState = searchViewState.value!!
        val shouldSearchForSuggestions = viewState.isInFocus
        searchViewState.value = viewState.copy(
                displayedText = query,
                isClearAvailable = viewState.isInFocus && query.isNotEmpty(),
                isLogoDisplayed = !viewState.isInFocus && query.isEmpty(),
                isLoadingSuggestions = viewState.isInFocus,
                cursorPosition = CursorPosition.KEEP)
        if (shouldSearchForSuggestions) {
            searchSuggestions.value = query.trim()
        }
    }

    protected fun submitQueryInternally(query: String) {
        searchViewState.value = searchViewState.value!!.copy(
                isInFocus = false,
                cursorPosition = CursorPosition.KEEP)
        searchResults.value = query.trim()
    }

    protected fun requestFocusGainInternally(isInFocus: Boolean) {
        val viewState = searchViewState.value!!
        searchViewState.value = viewState.copy(
                isInFocus = isInFocus,
                isClearAvailable = isInFocus && viewState.displayedText.isNotEmpty(),
                isLogoDisplayed = !isInFocus && viewState.displayedText.isEmpty(),
                isLoadingSuggestions = isInFocus,
                cursorPosition = CursorPosition.KEEP)
    }

    protected fun navigationIconClickedInternally() {
        val viewState = searchViewState.value!!
        when (viewState.isInFocus) {
            true -> searchViewState.value = viewState.copy(
                    isInFocus = false,
                    cursorPosition = CursorPosition.KEEP)
            else -> searchNavigation.value = SearchNavigationItem.DRAWER
        }
    }

    protected fun clearIconClickedInternally() {
        searchViewState.value = searchViewState.value!!.copy(
                displayedText = EMPTY_TEXT,
                cursorPosition = CursorPosition.KEEP)
    }

    protected fun textToSpeechIconClickedInternally() {
        searchNavigation.value = SearchNavigationItem.TEXT_TO_SPEECH
    }

    protected fun handleTextToSpeechResultInternally(result: TextToSpeechResult) {
        if (result is TextToSpeechResult.Successful) {
            searchViewState.value = searchViewState.value!!.copy(
                    isInFocus = true,
                    displayedText = result.text,
                    cursorPosition = CursorPosition.END)
        }
    }

}