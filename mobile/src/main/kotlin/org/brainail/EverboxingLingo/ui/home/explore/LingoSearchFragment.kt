package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lingo_search.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.ui.base.BaseViewModel
import org.brainail.EverboxingLingo.ui.base.ViewModelAwareFragment
import org.brainail.EverboxingLingo.ui.home.search.SearchViewModel
import org.brainail.EverboxingLingo.util.NavigableBack
import org.brainail.EverboxingLingo.util.ScrollablePage
import org.brainail.EverboxingLingo.util.extensions.getActivityViewModel
import org.brainail.EverboxingLingo.util.extensions.getViewModel
import org.brainail.EverboxingLingo.util.extensions.inflate
import org.brainail.EverboxingLingo.util.extensions.reObserve

class LingoSearchFragment :
        ViewModelAwareFragment<LingoSearchFragmentViewModel>(),
        NavigableBack, ScrollablePage {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var screenViewModel: LingoSearchFragmentViewModel

    private val searchResultsObserver = Observer<SuggestionModel> { it ->
        screenViewModel.searchResults(it!!)
    }

    private val searchSuggestionsObserver = Observer<String> {
        screenViewModel.searchSuggestions(it!!)
    }

    private val presentSuggestionsObserver = Observer<List<SuggestionModel>> {
        searchViewModel.suggestionsPrepared(it!!)
    }

    private val startSuggestionsLoadingObserver = Observer<Void> {
        searchViewModel.suggestionsStartedLoading()
    }

    private val viewStateObserver = Observer<LingoSearchFragmentViewState> {
        renderViewState(it!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_lingo_search)
    }

    override fun createPrimaryViewModels(): Array<BaseViewModel>? {
        screenViewModel = getViewModel(viewModelFactory)
        return arrayOf(screenViewModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearch()
    }

    override fun scrollToTop() {
        (searchResultsRecyclerView.layoutManager as? LinearLayoutManager)
                ?.scrollToPositionWithOffset(0, 0)
    }

    private fun initSearch() {
        searchViewModel = getActivityViewModel(viewModelFactory)
        searchViewModel.searchResults().reObserve(this, searchResultsObserver)
        searchViewModel.searchSuggestions().reObserve(this, searchSuggestionsObserver)

        screenViewModel.presentSuggestions().reObserve(this, presentSuggestionsObserver)
        screenViewModel.startSuggestionsLoading().reObserve(this, startSuggestionsLoadingObserver)
        screenViewModel.viewState().reObserve(this, viewStateObserver)

        // TODO("https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4")
        searchResultsRecyclerView.adapter = LingoSearchResultsAdapter()

        swipeRefreshView.isEnabled = false
        swipeRefreshView.setColorSchemeResources(
                R.color.material_pink_500,
                R.color.material_indigo_500,
                R.color.material_lime_500)
    }

    private fun renderViewState(viewState: LingoSearchFragmentViewState) {
        // items
        (searchResultsRecyclerView.adapter as LingoSearchResultsAdapter).submitList(viewState.displayedSearchResults)

        // refresh
        swipeRefreshView.isRefreshing = viewState.isLoadingSearchResults
    }

    companion object {
        const val FRAGMENT_TAG = "LingoSearchFragmentTag"

        @JvmStatic
        fun newInstance(): LingoSearchFragment = LingoSearchFragment()
    }
}
