package org.brainail.EverboxingLingo.ui.home.search

import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.util.extensions.EMPTY_TEXT

data class SearchViewState(
        val isInFocus: Boolean = false,
        val displayedText: String = EMPTY_TEXT,
        val isClearAvailable: Boolean = false,
        val cursorPosition: CursorPosition = CursorPosition.KEEP,
        val isTextToSpeechAvailable: Boolean = false,
        val isLogoDisplayed: Boolean = false,
        private val isLoadingSuggestions: Boolean = false,
        val isLoadingSearchResults: Boolean = false,
        val displayedSuggestions: List<SuggestionModel> = emptyList(),
        val displayedSearchResults: List<SearchResultModel> = emptyList()) {

    val displayLoading = isInFocus && isLoadingSuggestions

    companion object {
        val INITIAL by lazy {
            SearchViewState(
                    isInFocus = false,
                    isLogoDisplayed = true,
                    displayedText = EMPTY_TEXT,
                    isClearAvailable = false,
                    isTextToSpeechAvailable = true,
                    cursorPosition = CursorPosition.KEEP,
                    isLoadingSearchResults = false,
                    isLoadingSuggestions = false)
        }
    }

    enum class CursorPosition {
        /** Move to the end */
        END,
        /** Keep it as it is */
        KEEP
    }
}