package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lingo_search.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.extensions.reObserve
import org.brainail.EverboxingLingo.ui.ParcelableViewModelAwareFragment
import org.brainail.EverboxingLingo.ui.home.SearchViewModel
import org.brainail.EverboxingLingo.util.NavigableBack
import org.jetbrains.anko.toast

class LingoSearchFragment : ParcelableViewModelAwareFragment<LingoSearchFragmentViewModel>(), NavigableBack {

    private lateinit var searchViewModel: SearchViewModel

    private val searchResultsObserver = Observer<String> {
        activity?.toast("Search for results where query = $it")
    }

    private val searchSuggestionsObserver = Observer<String> {
        viewModel.searchSuggestions(it!!)
    }

    private val presentSuggestionsObserver = Observer<List<String>> {
        searchViewModel.suggestionsPrepared(it!!)
    }

    private val startSuggestionsLoadingObserver = Observer<Void> {
        searchViewModel.suggestionsStartedLoading()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lingo_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initSearch()
    }

    private fun initSearch() {
        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchViewModel::class.java)

        searchViewModel.searchResults().reObserve(this, searchResultsObserver)
        searchViewModel.searchSuggestions().reObserve(this, searchSuggestionsObserver)
        viewModel.presentSuggestions().reObserve(this, presentSuggestionsObserver)
        viewModel.startSuggestionsLoading().reObserve(this, startSuggestionsLoadingObserver)

        searchResultsRecyclerView.adapter = LingoSearchResultsAdapter()
    }

    override fun viewModelType() = LingoSearchFragmentViewModel::class.java

    companion object {
        const val FRAGMENT_TAG = "LingoSearchFragmentTag"

        @JvmStatic
        fun newInstance(): LingoSearchFragment = LingoSearchFragment()
    }

}
