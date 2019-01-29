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

package org.brainail.everboxing.lingo.ui.home.history

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_history.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.ui.home.search.results.SearchResultsFragment
import org.brainail.everboxing.lingo.util.extensions.getViewModel

class HistoryFragment : SearchResultsFragment() {
    override fun obtainScreenViewModel() = getViewModel<HistoryFragmentViewModel>(viewModelFactory)
    override fun pageLayoutId() = R.layout.fragment_history
    override fun recyclerView(): RecyclerView = searchResultsRecyclerView
    override fun refreshView(): SwipeRefreshLayout = swipeRefreshView
}
