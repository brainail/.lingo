package org.brainail.everboxing.lingo.ui.home.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.brainail.everboxing.lingo.databinding.ItemSearchResultBinding
import org.brainail.everboxing.lingo.model.SearchResultModel

class ExploreSearchResultsAdapter(private val searchResultClickListener: SearchResultClickListener? = null)
    : ListAdapter<SearchResultModel, ExploreSearchResultViewHolder>(diffCallback) {

    private val listenersDelegate = ListenersDelegate()

    interface SearchResultClickListener {
        fun onSearchResultClick(item: SearchResultModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreSearchResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreSearchResultViewHolder(binding, listenersDelegate)
    }

    override fun onBindViewHolder(holder: ExploreSearchResultViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ListenersDelegate : SearchResultClickListener {
        override fun onSearchResultClick(item: SearchResultModel) {
            searchResultClickListener?.onSearchResultClick(item)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SearchResultModel>() {
            override fun areItemsTheSame(oldItem: SearchResultModel, newItem: SearchResultModel): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SearchResultModel, newItem: SearchResultModel): Boolean =
                    oldItem == newItem
        }
    }
}
