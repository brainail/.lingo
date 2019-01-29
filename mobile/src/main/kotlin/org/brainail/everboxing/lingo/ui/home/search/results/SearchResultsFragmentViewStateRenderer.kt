/*
 * Copyright 2019 Malyshev Yegor
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

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.brainail.everboxing.lingo.util.extensions.scrollToTopOnSubmitList
import org.brainail.logger.L

/**
 * Renders [SearchResultsFragmentViewState] related components from [SearchResultsFragment]
 */
class SearchResultsFragmentViewStateRenderer(
    private val recyclerView: RecyclerView,
    private val refreshView: SwipeRefreshLayout
) {

    fun render(viewState: SearchResultsFragmentViewState) {
        L.i("render()")

        // loading
        refreshView.isRefreshing = viewState.isLoadingSearchResults

        // items
        (recyclerView.adapter as SearchResultsAdapter).submitList(viewState.displayedSearchResults)
        if (SearchResultsFragmentViewState.ScrollPosition.TOP == viewState.searchResultsScrollPosition) {
            recyclerView.scrollToTopOnSubmitList()
        }
    }
}
