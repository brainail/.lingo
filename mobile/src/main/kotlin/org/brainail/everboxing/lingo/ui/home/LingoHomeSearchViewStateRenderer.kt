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

package org.brainail.everboxing.lingo.ui.home

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.ui.home.search.SearchSuggestionsAdapter
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState
import org.brainail.everboxing.lingo.util.extensions.lockInAppBar
import org.brainail.everboxing.lingo.util.extensions.scrollToTopOnSubmitList
import org.brainail.everboxing.lingo.widget.AppCompatBottomAppBar
import org.brainail.flysearch.FloatingSearchView
import org.brainail.logger.L

/**
 * Renders [SearchViewState] related components from [LingoHomeActivity]
 */
class LingoHomeSearchViewStateRenderer(
    private val activity: AppCompatActivity,
    private val floatingSearchView: FloatingSearchView,
    private val appBarView: AppBarLayout,
    private val toolbarUnderlay: View,
    private val bottomAppBarView: AppCompatBottomAppBar
) {

    fun render(viewState: SearchViewState) {
        L.i("render()")

        // update text
        when (viewState.displayedText.isEmpty()) {
            true -> floatingSearchView.clearText() // use clear instead of simple set due to issues with system widget
            else -> if (viewState.displayedText != floatingSearchView.text.toString()) {
                floatingSearchView.text = viewState.displayedText // set only if new to get rid of recursive updates
            }
        }

        // cursor
        if (SearchViewState.CursorPosition.END == viewState.cursorPosition) {
            floatingSearchView.setSelection(viewState.displayedText.length)
        }

        // focus
        floatingSearchView.isActivated = viewState.isInFocus

        // icons
        floatingSearchView.showSearchLogo(viewState.isLogoDisplayed)
        floatingSearchView.menu.findItem(R.id.menu_clear)?.isVisible = viewState.isClearAvailable
        floatingSearchView.menu.findItem(R.id.menu_progress)?.isVisible = viewState.displayLoading
        floatingSearchView.menu.findItem(R.id.menu_tts)?.isVisible =
            viewState.isTextToSpeechAvailable && LingoHomeActivityNavigator.canShowTextToSpeech(activity)

        // items
        (floatingSearchView.adapter as SearchSuggestionsAdapter).submitList(viewState.displayedSuggestions)
        if (SearchViewState.ScrollPosition.TOP == viewState.suggestionsScrollPosition) {
            floatingSearchView.recyclerView.scrollToTopOnSubmitList()
        }

        // render visual appearance of other items only if we allow it,
        // in such a way we won't affect widgets which should be only affected for our home page
        if (floatingSearchView.isEnabled) {
            // scroll behavior
            appBarView.takeIf { viewState.isInFocus }?.setExpanded(true)
            toolbarUnderlay.lockInAppBar(viewState.isInFocus)

            // bottom navigation
            bottomAppBarView.hideOnScroll = !viewState.isInFocus
            bottomAppBarView.takeIf { viewState.isInFocus }?.show()
        }
    }
}
