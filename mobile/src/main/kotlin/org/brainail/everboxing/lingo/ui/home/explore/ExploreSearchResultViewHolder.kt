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

import org.brainail.everboxing.lingo.databinding.ItemSearchResultBinding
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BindingViewHolder

class ExploreSearchResultViewHolder(
    binding: ItemSearchResultBinding,
    searchResultClickListener: ExploreSearchResultsAdapter.SearchResultClickListener
) : BindingViewHolder<ItemSearchResultBinding>(binding) {

    init {
        binding.searchResultClickListener = searchResultClickListener
    }

    fun bindTo(item: SearchResultModel) {
        binding.searchResult = item
        binding.executePendingBindings()
    }
}
