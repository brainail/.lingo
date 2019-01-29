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

package org.brainail.everboxing.lingo.ui.home.search.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.util.ScrollablePage
import org.brainail.everboxing.lingo.util.extensions.getActivityViewModel
import org.brainail.everboxing.lingo.util.extensions.inflate
import org.brainail.everboxing.lingo.util.extensions.lifecycleAwareLazyFast
import org.brainail.everboxing.lingo.util.extensions.observeK
import org.brainail.everboxing.lingo.util.extensions.observeNonNull
import org.brainail.everboxing.lingo.util.extensions.pixelOffset
import org.brainail.everboxing.lingo.util.extensions.scrollToTop
import org.brainail.everboxing.lingo.util.ui.SwipeToActionCallback
import javax.inject.Inject
import org.brainail.everboxing.lingo.R.color.material_indigo_500 as colorIndigo500
import org.brainail.everboxing.lingo.R.color.material_lime_500 as colorLime500
import org.brainail.everboxing.lingo.R.color.material_pink_500 as colorPink500

abstract class SearchResultsFragment :
    ViewModelAwareFragment(),
    ScrollablePage, SearchResultsAdapter.SearchResultClickListener {

    @Inject
    lateinit var navigator: SearchResultsFragmentNavigator

    private val searchViewModel by lazyFast { getActivityViewModel<SearchViewModel>(viewModelFactory) }
    private val screenViewModel by lazyFast { obtainScreenViewModel() }

    @Suppress("LeakingThis")
    private val searchResultsFragmentViewStateRenderer by lifecycleAwareLazyFast(this) {
        SearchResultsFragmentViewStateRenderer(recyclerView(), refreshView())
    }

    private lateinit var searchResultsItemTouchHelper: ItemTouchHelper

    /**
     * Returns screen's main [androidx.lifecycle.ViewModel]
     */
    abstract fun obtainScreenViewModel(): SearchResultsFragmentViewModel

    /**
     * Returns screen's main __layout__
     */
    @LayoutRes
    abstract fun pageLayoutId(): Int

    /**
     * Returns screen's main list represented by [RecyclerView]
     */
    abstract fun recyclerView(): RecyclerView

    /**
     * Returns screen's main progress view represented by [SwipeRefreshLayout]
     */
    abstract fun refreshView(): SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(pageLayoutId())
    }

    override fun createPrimaryViewModels(): Array<BaseViewModel>? = arrayOf(screenViewModel)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchComponents()
    }

    override fun scrollToTop() {
        recyclerView().scrollToTop()
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
            .observeNonNull(viewLifecycleOwner) { searchResultsFragmentViewStateRenderer.render(it) }

        screenViewModel.navigateToSearchResultEvent()
            .observeNonNull(viewLifecycleOwner) { navigator.openWordDetails(it) }

        recyclerView().adapter = SearchResultsAdapter(this)

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
        searchResultsItemTouchHelper.attachToRecyclerView(recyclerView())

        refreshView().setColorSchemeResources(colorPink500, colorIndigo500, colorLime500)
    }
}
