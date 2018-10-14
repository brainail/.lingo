package org.brainail.everboxing.lingo.ui.home.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_lingo_search.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.util.NavigableBack
import org.brainail.everboxing.lingo.util.ScrollablePage
import org.brainail.everboxing.lingo.util.extensions.getActivityViewModel
import org.brainail.everboxing.lingo.util.extensions.getViewModel
import org.brainail.everboxing.lingo.util.extensions.inflate
import org.brainail.everboxing.lingo.util.ui.SwipeToActionCallback

class LingoSearchFragment :
        ViewModelAwareFragment(),
        NavigableBack, ScrollablePage {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var screenViewModel: LingoSearchFragmentViewModel

    private lateinit var searchResultsAdapter: LingoSearchResultsAdapter
    private lateinit var searchResultsItemTouchHelper: ItemTouchHelper

    private val searchResultsObserver = Observer<SuggestionModel> {
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
        searchViewModel.searchResults().observe(viewLifecycleOwner, searchResultsObserver)
        searchViewModel.searchSuggestions().observe(viewLifecycleOwner, searchSuggestionsObserver)

        screenViewModel.presentSuggestions().observe(viewLifecycleOwner, presentSuggestionsObserver)
        screenViewModel.startSuggestionsLoading().observe(viewLifecycleOwner, startSuggestionsLoadingObserver)
        screenViewModel.viewState().observe(viewLifecycleOwner, viewStateObserver)

        searchResultsAdapter = LingoSearchResultsAdapter()
        searchResultsRecyclerView.adapter = searchResultsAdapter

        searchResultsItemTouchHelper = ItemTouchHelper(object : SwipeToActionCallback(
                requireActivity(),
                IconInfo(R.drawable.ic_twotone_favorite_black_24dp, R.color.colorAccent),
                IconInfo(R.drawable.ic_twotone_delete_black_24dp, R.color.colorPrimarySecondary)) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> screenViewModel.forgetResultAt(viewHolder.adapterPosition)
                    ItemTouchHelper.RIGHT -> screenViewModel.favoriteResultAt(viewHolder.adapterPosition)
                }
            }
        })
        searchResultsItemTouchHelper.attachToRecyclerView(searchResultsRecyclerView)

        swipeRefreshView.isEnabled = false
        swipeRefreshView.setColorSchemeResources(
                R.color.material_pink_500, R.color.material_indigo_500, R.color.material_lime_500)
    }

    private fun renderViewState(viewState: LingoSearchFragmentViewState) {
        searchResultsAdapter.submitList(viewState.displayedSearchResults)
        swipeRefreshView.isRefreshing = viewState.isLoadingSearchResults
    }

    companion object {
        const val FRAGMENT_TAG = "LingoSearchFragmentTag"

        @JvmStatic
        fun newInstance(): LingoSearchFragment = LingoSearchFragment()
    }
}
