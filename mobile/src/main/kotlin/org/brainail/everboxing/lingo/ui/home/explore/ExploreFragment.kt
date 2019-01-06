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

package org.brainail.everboxing.lingo.ui.home.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_explore.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.ui.home.explore.ExploreSearchResultsAdapter.SearchResultClickListener
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.util.ScrollablePage
import org.brainail.everboxing.lingo.util.extensions.getActivityViewModel
import org.brainail.everboxing.lingo.util.extensions.getViewModel
import org.brainail.everboxing.lingo.util.extensions.inflate
import org.brainail.everboxing.lingo.util.extensions.observeK
import org.brainail.everboxing.lingo.util.extensions.observeNonNull
import org.brainail.everboxing.lingo.util.extensions.pixelOffset
import org.brainail.everboxing.lingo.util.ui.SwipeToActionCallback
import javax.inject.Inject
import org.brainail.everboxing.lingo.R.color.material_indigo_500 as colorIndigo500
import org.brainail.everboxing.lingo.R.color.material_lime_500 as colorLime500
import org.brainail.everboxing.lingo.R.color.material_pink_500 as colorPink500

class ExploreFragment :
    ViewModelAwareFragment(),
    ScrollablePage, SearchResultClickListener {

    @Inject
    lateinit var navigator: ExploreFragmentNavigator

    private val searchViewModel by lazyFast { getActivityViewModel<SearchViewModel>(viewModelFactory) }
    private val screenViewModel by lazyFast { getViewModel<ExploreFragmentViewModel>(viewModelFactory) }

    private lateinit var searchResultsAdapter: ExploreSearchResultsAdapter
    private lateinit var searchResultsItemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_explore)
    }

    override fun createPrimaryViewModels(): Array<BaseViewModel>? = arrayOf(screenViewModel)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchComponents()
    }

    override fun scrollToTop() {
        (searchResultsRecyclerView.layoutManager as? LinearLayoutManager)
            ?.scrollToPositionWithOffset(0, 0)
    }

    override fun onSearchResultClick(itemView: View, item: SearchResultModel) {
        screenViewModel.searchResultClicked(item)
    }

    private fun initSearchComponents() {
        searchViewModel.searchResults()
            .observeNonNull(viewLifecycleOwner) { screenViewModel.searchResults(it) }
        searchViewModel.searchSuggestions()
            .observeNonNull(viewLifecycleOwner) { screenViewModel.searchSuggestions(it) }

        screenViewModel.presentSuggestions()
            .observeNonNull(viewLifecycleOwner) { searchViewModel.suggestionsPrepared(it) }
        screenViewModel.startSuggestionsLoading()
            .observeK(viewLifecycleOwner) { searchViewModel.suggestionsStartedLoading() }
        screenViewModel.viewState()
            .observeNonNull(viewLifecycleOwner) { renderViewState(it) }

        screenViewModel.navigateToSearchResultEvent()
            .observeNonNull(viewLifecycleOwner) { navigator.openWordDetails(it) }

        searchResultsAdapter = ExploreSearchResultsAdapter(this)
        searchResultsRecyclerView.adapter = searchResultsAdapter

        searchResultsItemTouchHelper = ItemTouchHelper(object : SwipeToActionCallback(
            requireActivity(),
            IconInfo(
                R.drawable.ic_twotone_favorite_black_24dp,
                R.color.colorAccent,
                pixelOffset(R.dimen.material_component_lists_icon_left_padding)
            ),
            IconInfo(
                R.drawable.ic_twotone_delete_black_24dp,
                R.color.colorPrimarySecondary,
                pixelOffset(R.dimen.material_component_lists_icon_left_padding)
            )
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = when (direction) {
                ItemTouchHelper.LEFT -> screenViewModel.forgetSearchResultAt(viewHolder.adapterPosition)
                ItemTouchHelper.RIGHT -> screenViewModel.favoriteSearchResultAt(viewHolder.adapterPosition)
                else -> Unit
            }
        })
        searchResultsItemTouchHelper.attachToRecyclerView(searchResultsRecyclerView)

        swipeRefreshView.setColorSchemeResources(colorPink500, colorIndigo500, colorLime500)
    }

    private fun renderViewState(viewState: ExploreFragmentViewState) {
        searchResultsAdapter.submitList(viewState.displayedSearchResults)
        swipeRefreshView.isRefreshing = viewState.isLoadingSearchResults
    }
}
