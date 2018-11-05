package org.brainail.everboxing.lingo.ui.home.explore

import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange

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

    class SearchResultsPrepared(private val searchResults: List<SearchResultModel>) :
            PartialViewStateChange<LingoSearchFragmentViewState> {
        override fun applyTo(viewState: LingoSearchFragmentViewState): LingoSearchFragmentViewState {
            return viewState.copy(
                    isLoadingSearchResults = false,
                    displayedSearchResults = searchResults)
        }
    }

    class ForgetSearchResult(private val item: SearchResultModel) :
            PartialViewStateChange<LingoSearchFragmentViewState> {
        override fun applyTo(viewState: LingoSearchFragmentViewState): LingoSearchFragmentViewState {
            val newResults = viewState.displayedSearchResults.filter { it.id != item.id }
            return viewState.copy(displayedSearchResults = newResults)
        }
    }

    class FavoriteSearchResult(private val item: SearchResultModel) :
            PartialViewStateChange<LingoSearchFragmentViewState> {
        override fun applyTo(viewState: LingoSearchFragmentViewState): LingoSearchFragmentViewState {
            val newResults = viewState.displayedSearchResults.map {
                if (it.id != item.id) it else it.copy(favorite = !it.favorite) }
            return viewState.copy(displayedSearchResults = newResults)
        }
    }
}