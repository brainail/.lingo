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

import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange
import org.brainail.everboxing.lingo.util.TextToSpeechResult

data class SearchViewState(
    val isInFocus: Boolean = false,
    val displayedText: String = "",
    val isClearAvailable: Boolean = false,
    val cursorPosition: CursorPosition = CursorPosition.KEEP,
    val isTextToSpeechAvailable: Boolean = false,
    val isLogoDisplayed: Boolean = false,
    private val isLoadingSuggestions: Boolean = false,
    val displayedSuggestions: List<SuggestionModel> = emptyList(),
    val suggestionsScrollPosition: ScrollPosition = ScrollPosition.KEEP
) {

    val displayLoading = isInFocus && isLoadingSuggestions

    companion object {
        val INITIAL by lazyFast {
            SearchViewState(
                isInFocus = false,
                isLogoDisplayed = true,
                displayedText = "",
                isClearAvailable = false,
                isTextToSpeechAvailable = true,
                cursorPosition = CursorPosition.KEEP,
                isLoadingSuggestions = false,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }

    /**
     * [CursorPosition.END] - move to the end
     * [CursorPosition.KEEP] - keep it as it is
     */
    enum class CursorPosition { END, KEEP }

    /**
     * [ScrollPosition.TOP] - scroll to the top
     * [ScrollPosition.KEEP] - keep it as it is
     */
    enum class ScrollPosition { TOP, KEEP }

    object SuggestionsStartedLoading : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                isLoadingSuggestions = viewState.isInFocus, // only indicate progress if we are currently in focus
                cursorPosition = CursorPosition.KEEP,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }

    class SuggestionsPrepared(
        private val suggestions: List<SuggestionModel>
    ) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                isLoadingSuggestions = false,
                displayedSuggestions = suggestions,
                cursorPosition = CursorPosition.KEEP,
                suggestionsScrollPosition = when (viewState.isLoadingSuggestions) {
                    true -> ScrollPosition.TOP
                    else -> ScrollPosition.KEEP
                }
            )
        }
    }

    class UpdateQuery(private val query: String) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                displayedText = query,
                isClearAvailable = viewState.isInFocus && query.isNotEmpty(),
                isLogoDisplayed = !viewState.isInFocus && query.isEmpty(),
                cursorPosition = CursorPosition.KEEP,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }

    class SubmitQuery(private val query: String) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                isInFocus = false,
                displayedText = query,
                cursorPosition = CursorPosition.KEEP,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }

    class RequestFocusGain(
        private val isInFocus: Boolean,
        private val newCursorPosition: CursorPosition = CursorPosition.KEEP
    ) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                isInFocus = isInFocus,
                isClearAvailable = isInFocus && viewState.displayedText.isNotEmpty(),
                isLogoDisplayed = !isInFocus && viewState.displayedText.isEmpty(),
                cursorPosition = newCursorPosition,
                suggestionsScrollPosition = if (isInFocus) ScrollPosition.TOP else ScrollPosition.KEEP
            )
        }
    }

    object NavigationIconClickedInFocus : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                isInFocus = false,
                cursorPosition = CursorPosition.KEEP,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }

    object ClearIconClicked : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                displayedText = "",
                cursorPosition = CursorPosition.KEEP,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }

    class TextToSpeechResultSuccess(
        private val result: TextToSpeechResult.Successful
    ) : PartialViewStateChange<SearchViewState> {
        override fun applyTo(viewState: SearchViewState): SearchViewState {
            return viewState.copy(
                isInFocus = true,
                displayedText = result.text,
                cursorPosition = CursorPosition.END,
                suggestionsScrollPosition = ScrollPosition.KEEP
            )
        }
    }
}
