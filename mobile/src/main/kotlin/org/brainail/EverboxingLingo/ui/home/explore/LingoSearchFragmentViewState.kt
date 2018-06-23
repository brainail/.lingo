package org.brainail.EverboxingLingo.ui.home.explore

import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.ui.base.PartialViewStateChange

data class LingoSearchFragmentViewState(
        val isLoadingSearchResults: Boolean = false,
        val displayedSearchResults: List<SearchResultModel> = emptyList()) {

    companion object {
        val INITIAL by lazy {
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