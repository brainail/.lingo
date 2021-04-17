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

package org.brainail.everboxing.lingo.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_lingo_home.*
import kotlinx.android.synthetic.main.fragment_word_details.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.base.util.consume
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.ui.base.ViewModelInitialArgs
import org.brainail.everboxing.lingo.util.MenuItemClickHandler
import org.brainail.everboxing.lingo.util.ViewClickHandler
import org.brainail.everboxing.lingo.util.extensions.getViewModel
import org.brainail.everboxing.lingo.util.extensions.lifecycleAwareLazyFast
import org.brainail.everboxing.lingo.util.extensions.observeNonNull

class WordDetailsFragment
    : ViewModelAwareFragment(), MenuItemClickHandler, ViewClickHandler {

    private val viewStateRenderer by lifecycleAwareLazyFast(this) {
        WordDetailsFragmentViewStateRenderer(
            wordTitleView,
            wordDefinitionView,
            wordExampleView,
            activity!!.actionButtonView
        )
    }

    private val screenViewModel by lazyFast {
        getViewModel<WordDetailsFragmentViewModel>(viewModelFactory)
    }

    override fun createPrimaryViewModels(): Array<BaseViewModel> = arrayOf(screenViewModel)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        screenViewModel.viewState().observeNonNull(viewLifecycleOwner) {
            viewStateRenderer.render(it)
        }
    }

    override fun getViewModelInitialArgs(
        bundle: Bundle?,
        type: Class<out BaseViewModel>
    ): ViewModelInitialArgs? {
        return bundle?.let { WordDetailsFragmentInitialArgs(WordDetailsFragmentArgs.fromBundle(it)) }
    }

    override fun handleMenuItemClick(menu: MenuItem) = when (menu.itemId) {
        R.id.menuDetailsDeleteItem -> consume { TODO() }
        R.id.menuDetailsShareItem -> consume { TODO() }
        else -> super.handleMenuItemClick(menu)
    }

    override fun handleViewClick(view: View): Boolean {
        when (view.tag) {
            R.id.detailsFavouriteActionButtonView -> return consume { TODO() }
        }
        return super.handleViewClick(view)
    }
}
