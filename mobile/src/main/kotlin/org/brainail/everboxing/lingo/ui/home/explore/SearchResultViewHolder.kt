package org.brainail.everboxing.lingo.ui.home.explore

import org.brainail.everboxing.lingo.databinding.ItemSearchResultBinding
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BindingViewHolder

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