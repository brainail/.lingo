package org.brainail.EverboxingLingo.ui.home.explore

import org.brainail.EverboxingLingo.databinding.ItemSearchResultBinding
import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.ui.base.BindingViewHolder

class SearchResultViewHolder(
        binding: ItemSearchResultBinding,
        searchResultClickListener: LingoSearchResultsAdapter.SearchResultClickListener)
    : BindingViewHolder<ItemSearchResultBinding>(binding) {

    init {
        binding.searchResultClickListener = searchResultClickListener
    }

    fun bindTo(item: SearchResultModel) {
        binding.searchResult = item
        binding.executePendingBindings()
    }
}