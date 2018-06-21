package org.brainail.EverboxingLingo.ui.home.search

import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.util.extensions.EMPTY_TEXT

sealed class PartialViewStateChanges {
    abstract fun applyTo(viewState: SearchViewState): SearchViewState

    object SuggestionsStartedLoading: PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    // only indicate progress if we are currently in focus
                    isLoadingSuggestions = viewState.isInFocus)
        }
    }

    object SearchResultsStartedLoading: PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(isLoadingSearchResults = true)
        }
    }

    data class SearchResultsPrepared(private val searchResults: List<SearchResultModel>): PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isLoadingSearchResults = false,
                    displayedSearchResults = searchResults)
        }
    }

    data class SuggestionsPrepared(private val suggestions: List<SuggestionModel>): PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isLoadingSuggestions = false,
                    displayedSuggestions = suggestions)
        }
    }

    data class UpdateQuery(private val query: String): PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    displayedText = query,
                    isClearAvailable = viewState.isInFocus && query.isNotEmpty(),
                    isLogoDisplayed = !viewState.isInFocus && query.isEmpty(),
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    data class SubmitQuery(private val query: String): PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = false,
                    displayedText = query,
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    data class RequestFocusGain(private val isInFocus: Boolean): PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = isInFocus,
                    isClearAvailable = isInFocus && viewState.displayedText.isNotEmpty(),
                    isLogoDisplayed = !isInFocus && viewState.displayedText.isEmpty(),
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    object NavigationIconClickedInFocus: PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = false,
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    object ClearIconClicked: PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    displayedText = EMPTY_TEXT,
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    data class TextToSpeechResultSuccess(private val result: TextToSpeechResult.Successful): PartialViewStateChanges() {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = true,
                    displayedText = result.text,
                    cursorPosition = SearchViewState.CursorPosition.END)
        }
    }
}