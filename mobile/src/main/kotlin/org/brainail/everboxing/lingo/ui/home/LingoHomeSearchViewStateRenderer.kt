package org.brainail.everboxing.lingo.ui.home

import android.content.Context
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.ui.home.search.SearchSuggestionsAdapter
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState
import org.brainail.everboxing.lingo.widget.AppCompatBottomAppBar
import org.brainail.flysearch.FloatingSearchView
import org.brainail.logger.L

/**
 * Renders [SearchViewState] related components from [LingoHomeActivity]
 */
class LingoHomeSearchViewStateRenderer(
        private val context: Context,
        private val floatingSearchView: FloatingSearchView,
        private val appBarView: AppBarLayout,
        private val toolbarUnderlay: View,
        private val bottomAppBarView: AppCompatBottomAppBar) {

    fun renderSearchViewState(viewState: SearchViewState) {
        L.i("renderSearchViewState: viewState = $viewState")

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
                viewState.isTextToSpeechAvailable && LingoHomeActivityNavigator.canShowTextToSpeech(context)

        // items
        (floatingSearchView.adapter as SearchSuggestionsAdapter).submitList(viewState.displayedSuggestions)

        // scroll behavior
        appBarView.takeIf { viewState.isInFocus }?.setExpanded(true)
        floatingSearchView.post {
            // post it to get rid of flickering effect
            val toolbarUnderlayLp = toolbarUnderlay.layoutParams as AppBarLayout.LayoutParams
            val newScrollFlags = if (viewState.isInFocus) 0 else NO_FOCUS_SEARCH_SCROLL_FLAGS
            toolbarUnderlayLp.takeIf { it.scrollFlags != newScrollFlags }?.apply {
                scrollFlags = newScrollFlags
                toolbarUnderlay.layoutParams = this // important in order to have the proper effect
            }
        }

        // bottom navigation
        bottomAppBarView.hideOnScroll = !viewState.isInFocus
        bottomAppBarView.takeIf { viewState.isInFocus }?.show()
    }

    companion object {
        private const val NO_FOCUS_SEARCH_SCROLL_FLAGS = SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP or SCROLL_FLAG_ENTER_ALWAYS
    }
}