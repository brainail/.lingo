package org.brainail.EverboxingLingo.ui.home.explore

import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.ui.base.PartialViewStateChange
import org.brainail.EverboxingLingo.util.extensions.lazyFast

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