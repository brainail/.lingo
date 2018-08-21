package org.brainail.everboxing.lingo.ui.home.explore

import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange
import org.brainail.everboxing.lingo.util.extensions.lazyFast

data class LingoSearchFragmentViewState(
        val isLoadingSearchResults: Boolean = false,
        val displayedSearchResults: List<SearchResultModel> = emptyList()) {

    companion object {
        val INITIAL by lazyFast {
            LingoSearchFragmentViewState(
                    isLoadingSearchResults = false)
        }
    }

    object SearchResultsStartedLoading : PartialViewStateChange<LingoSearchFragmentViewState> {
        override fun applyTo(viewState: LingoSearchFragmentViewState): LingoSearchFragmentViewState {
            return viewState.copy(isLoadingSearchResults = true)
        }
    }

    data class SearchResultsPrepared(private val searchResults: List<SearchResultModel>) :
            PartialViewStateChange<LingoSearchFragmentViewState> {
        override fun applyTo(viewState: LingoSearchFragmentViewState): LingoSearchFragmentViewState {
            return viewState.copy(
                    isLoadingSearchResults = false,
                    displayedSearchResults = searchResults)
        }
    }
}