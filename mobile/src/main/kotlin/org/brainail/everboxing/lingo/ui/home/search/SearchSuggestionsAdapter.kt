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

package org.brainail.everboxing.lingo.ui.home.search

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_suggestion.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.home.search.SearchSuggestionsAdapter.SuggestionViewHolder
import org.brainail.everboxing.lingo.util.extensions.inflate

class SearchSuggestionsAdapter(
    private val suggestionClickListener: SuggestionClickListener? = null
) : ListAdapter<SuggestionModel, SuggestionViewHolder>(diffCallback) {

    private val listenersDelegate = ListenersDelegate()

    interface SuggestionClickListener {
        fun onSuggestionClick(item: SuggestionModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(parent.inflate(R.layout.item_suggestion), listenersDelegate)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class SuggestionViewHolder(
        override val containerView: View,
        private val suggestionClickListener: SuggestionClickListener
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private lateinit var suggestionItem: SuggestionModel

        fun bindTo(item: SuggestionModel) {
            suggestionItem = item

            suggestionItemIconView.setImageDrawable(
                when (item.isRecent) {
                    true -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_history_control_24dp)
                    else -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_search_black_24dp)
                }
            )

            suggestionItemTitleView.text = item.word
            suggestionItemDescriptionView.text = item.description
            suggestionItemDescriptionView.isVisible = !item.description.isEmpty()
            itemView.setOnClickListener { suggestionClickListener.onSuggestionClick(suggestionItem) }
        }
    }

    inner class ListenersDelegate : SuggestionClickListener {
        override fun onSuggestionClick(item: SuggestionModel) {
            suggestionClickListener?.onSuggestionClick(item)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SuggestionModel>() {
            override fun areItemsTheSame(oldItem: SuggestionModel, newItem: SuggestionModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SuggestionModel, newItem: SuggestionModel): Boolean =
                oldItem == newItem
        }
    }
}
