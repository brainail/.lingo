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

package org.brainail.everboxing.lingo.ui.home.history

import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.usecase.FindHistorySearchResultsUseCase
import org.brainail.everboxing.lingo.domain.usecase.FindHistorySuggestionsUseCase
import org.brainail.everboxing.lingo.domain.usecase.ForgetSearchResultUseCase
import org.brainail.everboxing.lingo.domain.usecase.SaveSearchResultInHistoryUseCase
import org.brainail.everboxing.lingo.domain.usecase.SaveSuggestionUseCase
import org.brainail.everboxing.lingo.domain.usecase.ToggleSearchResultInFavoritesUseCase
import org.brainail.everboxing.lingo.mapper.SearchResultModelMapper
import org.brainail.everboxing.lingo.mapper.SuggestionModelMapper
import org.brainail.everboxing.lingo.ui.home.search.results.SearchResultsFragmentViewModel
import javax.inject.Inject

class HistoryFragmentViewModel @Inject constructor(
    private val findSuggestionsUseCase: FindHistorySuggestionsUseCase,
    private val findSearchResultsUseCase: FindHistorySearchResultsUseCase,
    saveRecentSuggestionUseCase: SaveSuggestionUseCase,
    toggleSearchResultInFavoritesUseCase: ToggleSearchResultInFavoritesUseCase,
    saveSearchResultInHistoryUseCase: SaveSearchResultInHistoryUseCase,
    forgetSearchResultUseCase: ForgetSearchResultUseCase,
    suggestionModelMapper: SuggestionModelMapper,
    searchResultModelMapper: SearchResultModelMapper,
    appExecutors: AppExecutors
) : SearchResultsFragmentViewModel(
    saveRecentSuggestionUseCase,
    toggleSearchResultInFavoritesUseCase,
    saveSearchResultInHistoryUseCase,
    forgetSearchResultUseCase,
    suggestionModelMapper,
    searchResultModelMapper,
    appExecutors
) {

    override fun executeFindSuggestionsUseCase(query: String) = findSuggestionsUseCase.execute(query)
    override fun executeFindSearchResultsUseCase(query: String) = findSearchResultsUseCase.execute(query)
}
