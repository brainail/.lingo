package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lingo_search.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.ui.base.ParcelableViewModelAwareFragment
import org.brainail.EverboxingLingo.ui.home.search.SearchViewModel
import org.brainail.EverboxingLingo.util.NavigableBack
import org.brainail.EverboxingLingo.util.ScrollablePage
import org.brainail.EverboxingLingo.util.extensions.reObserve
import org.brainail.logger.L

class LingoSearchFragment :
        ParcelableViewModelAwareFragment<LingoSearchFragmentViewModel>(),
        NavigableBack, ScrollablePage {

    private lateinit var searchViewModel: SearchViewModel

    private val searchResultsObserver = Observer<String> { it ->
        viewModel.searchResults(it!!)
    }

    private val searchSuggestionsObserver = Observer<String> {
        viewModel.searchSuggestions(it!!)
    }

    private val presentSuggestionsObserver = Observer<List<SuggestionModel>> {
        searchViewModel.suggestionsPrepared(it!!)
    }

    private val startSuggestionsLoadingObserver = Observer<Void> {
        searchViewModel.suggestionsStartedLoading()
    }

    // TODO https://stackoverflow.com/questions/48966985/android-room-livedata-select-query-parameters
    private val presentSearchResultsObserver = Observer<List<SearchResultModel>> {
        L.v("presentSearchResultsObserver: $it")
    }

    private val startSearchResultsLoadingObserver = Observer<Void> {
        L.v("startSearchResultsLoadingObserver")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lingo_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initSearch()
    }

    override fun scrollToTop() {
        (searchResultsRecyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(0, 0)
    }

    private fun initSearch() {
        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchViewModel::class.java)

        searchViewModel.searchResults().reObserve(this, searchResultsObserver)
        searchViewModel.searchSuggestions().reObserve(this, searchSuggestionsObserver)
        viewModel.presentSuggestions().reObserve(this, presentSuggestionsObserver)
        viewModel.startSuggestionsLoading().reObserve(this, startSuggestionsLoadingObserver)
        viewModel.presentSearchResults().reObserve(this, presentSearchResultsObserver)
        viewModel.startSearchResultsLoading().reObserve(this, startSearchResultsLoadingObserver)

        // TODO("https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4")
        searchResultsRecyclerView.adapter = LingoSearchResultsAdapter()
    }

    override fun viewModelType() = LingoSearchFragmentViewModel::class.java

    companion object {
        const val FRAGMENT_TAG = "LingoSearchFragmentTag"

        @JvmStatic
        fun newInstance(): LingoSearchFragment = LingoSearchFragment()
    }
}
