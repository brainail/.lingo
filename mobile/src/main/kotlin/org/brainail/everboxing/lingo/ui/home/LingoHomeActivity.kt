package org.brainail.everboxing.lingo.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.mapper.TextToSpeechResultMapper
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareActivity
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityNavigator.Companion.REQ_CODE_SPEECH_INPUT
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityViewModel.NavigationItem
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityViewModel.NavigationTabItem
import org.brainail.everboxing.lingo.ui.home.LingoSearchSuggestionsAdapter.SuggestionClickListener
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel.SearchNavigationItem
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState
import org.brainail.everboxing.lingo.ui.home.search.SearchViewState.CursorPosition
import org.brainail.everboxing.lingo.util.TextWatcherAdapter
import org.brainail.everboxing.lingo.util.extensions.checkAllMatched
import org.brainail.everboxing.lingo.util.extensions.consume
import org.brainail.everboxing.lingo.util.extensions.getViewModel
import org.brainail.logger.L
import org.jetbrains.anko.toast
import javax.inject.Inject

class LingoHomeActivity : ViewModelAwareActivity(), SuggestionClickListener {
    @Inject
    lateinit var navigator: LingoHomeActivityNavigator

    @Inject
    internal lateinit var textToSpeechResultMapper: TextToSpeechResultMapper

    private lateinit var screenViewModel: LingoHomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()
        initSearch()
        initViewState()
    }

    override fun createPrimaryViewModels(): Array<BaseViewModel>? {
        screenViewModel = getViewModel(viewModelFactory)
        return arrayOf(screenViewModel)
    }

    override fun getLayoutResourceId() = R.layout.activity_lingo_home

    private fun initViewState() {
        screenViewModel.searchViewState().observe(this, Observer { renderSearchViewState(it!!) })
    }

    private fun renderSearchViewState(viewState: SearchViewState) {
        L.i("renderSearchViewState: viewState = $viewState")

        // update text
        when (viewState.displayedText.isEmpty()) {
            true -> floatingSearchView.clearText() // use clear instead of simple set due to issues with system widget
            else -> if (viewState.displayedText != floatingSearchView.text.toString()) {
                floatingSearchView.text = viewState.displayedText // set only if new to get rid of recursive updates
            }
        }

        // cursor
        if (CursorPosition.END == viewState.cursorPosition) {
            floatingSearchView.setSelection(viewState.displayedText.length)
        }

        // focus
        floatingSearchView.isActivated = viewState.isInFocus

        // icons
        floatingSearchView.showSearchLogo(viewState.isLogoDisplayed)
        floatingSearchView.menu.findItem(R.id.menu_clear)?.isVisible = viewState.isClearAvailable
        floatingSearchView.menu.findItem(R.id.menu_progress)?.isVisible = viewState.displayLoading
        floatingSearchView.menu.findItem(R.id.menu_tts)?.isVisible =
                viewState.isTextToSpeechAvailable && navigator.canShowTextToSpeech()

        // items
        (floatingSearchView.adapter as LingoSearchSuggestionsAdapter).submitList(viewState.displayedSuggestions)

        // scroll behavior
        floatingSearchView.post {
            // post it to get rid of flickering effect
            val toolbarUnderlayLp = toolbarUnderlay.layoutParams as AppBarLayout.LayoutParams
            val newScrollFlags = if (viewState.isInFocus) 0 else (SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP or SCROLL_FLAG_ENTER_ALWAYS)
            toolbarUnderlayLp.takeIf { it.scrollFlags != newScrollFlags }?.apply {
                scrollFlags = newScrollFlags
                toolbarUnderlay.layoutParams = this // important in order to have the proper effect
            }
        }

        // bottom navigation
        bottomAppBarView.hideOnScroll = !viewState.isInFocus
        bottomAppBarView.takeIf { viewState.isInFocus }?.show()
    }

    private fun initNavigation() {
        bottomAppBarView.replaceMenu(R.menu.menu_home_bottom_bar)
        bottomAppBarView.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_explore -> consume { screenViewModel.navigateTabTo(NavigationTabItem.EXPLORE) }
                R.id.menu_favorite -> consume { screenViewModel.navigateTabTo(NavigationTabItem.FAVOURITE) }
                R.id.menu_history -> consume { screenViewModel.navigateTabTo(NavigationTabItem.HISTORY) }
                else -> false
            }
        }

        screenViewModel.navigation().observe(this, Observer { navigateTo(it!!) })
        screenViewModel.navigationTab().observe(this, Observer { navigateTabTo(it!!) })
        screenViewModel.searchNavigation().observe(this, Observer { navigateTo(it!!) })
    }

    private fun initSearch() {
        floatingSearchView.apply {
            icon = DrawerArrowDrawable(this@LingoHomeActivity)
            setOnIconClickListener { screenViewModel.navigationIconClicked() }
            setOnSearchListener { query -> screenViewModel.submitQuery(query.toString()) }
            setOnSearchFocusChangedListener { screenViewModel.requestFocusGain(it) }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_clear -> consume { screenViewModel.clearIconClicked() }
                    R.id.menu_tts -> consume { screenViewModel.textToSpeechIconClicked() }
                    else -> false
                }
            }
            addTextChangedListener(object : TextWatcherAdapter() {
                override fun afterTextChanged(query: Editable) {
                    screenViewModel.updateQuery(query.toString())
                }
            })
            adapter = LingoSearchSuggestionsAdapter(this@LingoHomeActivity)
        }
    }

    override fun onSuggestionClick(item: SuggestionModel) {
        L.i("onSuggestionClick: $item")
        screenViewModel.suggestionClicked(item)
    }

    private fun navigateTo(navigationItem: NavigationItem) {
        L.v("navigateTo: navigationItem = $navigationItem")
        when (navigationItem) {
            NavigationItem.BACKWARD -> navigator.goBack()
            NavigationItem.SCROLL_TO_TOP -> navigator.scrollToTop()
        }.checkAllMatched
    }

    private fun navigateTabTo(navigationTabItem: LingoHomeActivityViewModel.NavigationTabItem) {
        L.v("navigateTabTo: navigationTabItem = $navigationTabItem")
        when (navigationTabItem) {
            NavigationTabItem.EXPLORE -> {
                navigator.showExploreSubScreen()
                selectMenuItem(R.id.menu_explore)
            }
            NavigationTabItem.FAVOURITE -> {
                navigator.showExploreSubScreen()
                selectMenuItem(R.id.menu_favorite)
            }
            NavigationTabItem.HISTORY -> {
                navigator.showExploreSubScreen()
                selectMenuItem(R.id.menu_history)
            }
        }.checkAllMatched
    }

    private fun selectMenuItem(menuItemId: Int) {
        val menu = bottomAppBarView.menu
        when (menuItemId) {
            R.id.menu_explore -> {
                menu.findItem(R.id.menu_explore)?.setIcon(R.drawable.ic_translate_24dp)
                menu.findItem(R.id.menu_favorite)?.setIcon(R.drawable.ic_favorite_outline_24dp)
                menu.findItem(R.id.menu_history)?.setIcon(R.drawable.ic_history_outline_24dp)
            }
            R.id.menu_favorite -> {
                menu.findItem(R.id.menu_explore)?.setIcon(R.drawable.ic_translate_outline_24dp)
                menu.findItem(R.id.menu_favorite)?.setIcon(R.drawable.ic_favorite_24dp)
                menu.findItem(R.id.menu_history)?.setIcon(R.drawable.ic_history_outline_24dp)
            }
            R.id.menu_history -> {
                menu.findItem(R.id.menu_explore)?.setIcon(R.drawable.ic_translate_outline_24dp)
                menu.findItem(R.id.menu_favorite)?.setIcon(R.drawable.ic_favorite_outline_24dp)
                menu.findItem(R.id.menu_history)?.setIcon(R.drawable.ic_history_24dp)
            }
        }
    }

    private fun navigateTo(navigationItem: SearchViewModel.SearchNavigationItem) {
        L.v("navigateTo: navigationItem = $navigationItem")
        when (navigationItem) {
            SearchNavigationItem.DRAWER -> toast("open Drawer please")
            SearchNavigationItem.TEXT_TO_SPEECH -> navigator.showTextToSpeech("What are you looking for?")
        }.checkAllMatched
    }

    override fun onBackPressed() {
        screenViewModel.navigateTo(NavigationItem.BACKWARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                screenViewModel.handleTextToSpeechResult(textToSpeechResultMapper.transform(resultCode, data))
            }
        }
    }
}
