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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.brainail.everboxing.lingo.databinding.ItemSearchResultBinding
import org.brainail.everboxing.lingo.model.SearchResultModel

class ExploreSearchResultsAdapter(
    private val searchResultClickListener: SearchResultClickListener? = null
) : ListAdapter<SearchResultModel, ExploreSearchResultViewHolder>(diffCallback) {

    private val listenersDelegate = ListenersDelegate()

    interface SearchResultClickListener {
        fun onSearchResultClick(itemView: View, item: SearchResultModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreSearchResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreSearchResultViewHolder(binding, listenersDelegate)
    }

    override fun onBindViewHolder(holder: ExploreSearchResultViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ListenersDelegate : SearchResultClickListener {
        override fun onSearchResultClick(itemView: View, item: SearchResultModel) {
            searchResultClickListener?.onSearchResultClick(itemView, item)
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
