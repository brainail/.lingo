package org.brainail.EverboxingLingo.ui.home.search

import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.base.PartialViewStateChange

data class SearchViewState(
        val isInFocus: Boolean = false,
        val displayedText: String = "",
        val isClearAvailable: Boolean = false,
        val cursorPosition: CursorPosition = CursorPosition.KEEP,
        val isTextToSpeechAvailable: Boolean = false,
        val isLogoDisplayed: Boolean = false,
        private val isLoadingSuggestions: Boolean = false,
        val displayedSuggestions: List<SuggestionModel> = emptyList()) {

    val displayLoading = isInFocus && isLoadingSuggestions

    companion object {
        val INITIAL by lazy {
            SearchViewState(
                    isInFocus = false,
                    isLogoDisplayed = true,
                    displayedText = "",
                    isClearAvailable = false,
                    isTextToSpeechAvailable = true,
                    cursorPosition = CursorPosition.KEEP,
                    isLoadingSuggestions = false)
        }
    }

    enum class CursorPosition {
        /** Move to the end */
        END,
        /** Keep it as it is */
        KEEP
    }

    object SuggestionsStartedLoading : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    // only indicate progress if we are currently in focus
                    isLoadingSuggestions = viewState.isInFocus)
        }
    }

    data class SuggestionsPrepared(private val suggestions: List<SuggestionModel>) :
            PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isLoadingSuggestions = false,
                    displayedSuggestions = suggestions)
        }
    }

    data class UpdateQuery(private val query: String) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    displayedText = query,
                    isClearAvailable = viewState.isInFocus && query.isNotEmpty(),
                    isLogoDisplayed = !viewState.isInFocus && query.isEmpty(),
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    data class SubmitQuery(private val query: String) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = false,
                    displayedText = query,
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    data class RequestFocusGain(private val isInFocus: Boolean) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = isInFocus,
                    isClearAvailable = isInFocus && viewState.displayedText.isNotEmpty(),
                    isLogoDisplayed = !isInFocus && viewState.displayedText.isEmpty(),
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    object NavigationIconClickedInFocus : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = false,
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    object ClearIconClicked : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    displayedText = "",
                    cursorPosition = SearchViewState.CursorPosition.KEEP)
        }
    }

    data class TextToSpeechResultSuccess(private val result: TextToSpeechResult.Successful) :
            PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                    isInFocus = true,
                    displayedText = result.text,
                    cursorPosition = SearchViewState.CursorPosition.END)
        }
    }
}