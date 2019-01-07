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

package org.brainail.everboxing.lingo.ui.home.search.results

import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange

data class SearchResultsFragmentViewState(
    val isLoadingSearchResults: Boolean = false,
    val displayedSearchResults: List<SearchResultModel> = emptyList()
) {

    companion object {
        val INITIAL by lazyFast {
            SearchResultsFragmentViewState(isLoadingSearchResults = false)
        }
    }

    object SearchResultsStartedLoading : PartialViewStateChange<SearchResultsFragmentViewState> {
        override fun applyTo(viewState: SearchResultsFragmentViewState): SearchResultsFragmentViewState {
            return viewState.copy(isLoadingSearchResults = true)
        }
    }

    class SearchResultsPrepared(private val searchResults: List<SearchResultModel>) :
        PartialViewStateChange<SearchResultsFragmentViewState> {
        override fun applyTo(viewState: SearchResultsFragmentViewState): SearchResultsFragmentViewState {
            return viewState.copy(
                isLoadingSearchResults = false,
                displayedSearchResults = searchResults
            )
        }
    }

    class ForgetSearchResult(private val item: SearchResultModel) :
        PartialViewStateChange<SearchResultsFragmentViewState> {
        override fun applyTo(viewState: SearchResultsFragmentViewState): SearchResultsFragmentViewState {
            val newResults = viewState.displayedSearchResults.filter { it.id != item.id }
            return viewState.copy(displayedSearchResults = newResults)
        }
    }

    class FavoriteSearchResult(private val item: SearchResultModel) :
        PartialViewStateChange<SearchResultsFragmentViewState> {
        override fun applyTo(viewState: SearchResultsFragmentViewState): SearchResultsFragmentViewState {
            val newResults = viewState.displayedSearchResults.map {
                if (it.id != item.id) it else it.copy(favorite = !it.favorite)
            }
            return viewState.copy(displayedSearchResults = newResults)
        }
    }
}
