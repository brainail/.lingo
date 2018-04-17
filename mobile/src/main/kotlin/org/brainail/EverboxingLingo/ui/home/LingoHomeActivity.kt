package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.text.Editable
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.mapper.TextToSpeechResultMapper
import org.brainail.EverboxingLingo.ui.ParcelableViewModelAwareActivity
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityNavigator.Companion.REQ_CODE_SPEECH_INPUT
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationTabItem
import org.brainail.EverboxingLingo.ui.home.SearchViewModel.SearchNavigationItem
import org.brainail.EverboxingLingo.ui.home.SearchViewState.CursorPosition
import org.brainail.EverboxingLingo.util.TextWatcherAdapter
import org.brainail.logger.L
import org.jetbrains.anko.toast
import javax.inject.Inject

class LingoHomeActivity : ParcelableViewModelAwareActivity<LingoHomeActivityViewModel>() {

    @Inject
    lateinit var navigator: LingoHomeActivityNavigator

    @Inject
    internal lateinit var textToSpeechResultMapper: TextToSpeechResultMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()
        initSearch()
        initViewState()
    }

    override fun layoutResId() = R.layout.activity_lingo_home

    private fun initViewState() {
        viewModel.searchViewState().observe(this, Observer { renderSearchViewState(it!!) })
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
        showClearButton(viewState.isClearAvailable)
        showSearchProgress(viewState.displayLoading)
        showTextToSpeechIcon(viewState.isTextToSpeechAvailable && navigator.canShowTextToSpeech())

        (floatingSearchView.adapter as LingoSearchSuggestionsAdapter).submitList(viewState.displayedSuggestions)

        // scroll behavior
        floatingSearchView.post { // post it to get rid of flickering effect
            val toolbarUnderlayLp = toolbarUnderlay.layoutParams as AppBarLayout.LayoutParams
            val newScrollFlags = if (viewState.isInFocus) 0 else (SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS)
            toolbarUnderlayLp.takeIf { it.scrollFlags != newScrollFlags }?.apply { scrollFlags = newScrollFlags }
        }
    }

    private fun initNavigation() {
        bottomNavigationBarView
                .addItem(BottomNavigationItem(R.drawable.ic_translate_24dp, R.string.tab_explore)
                        .setActiveColor(ContextCompat.getColor(this, R.color.tab_explore)))
                .addItem(BottomNavigationItem(R.drawable.ic_favorite_24dp, R.string.tab_favourite)
                        .setActiveColor(ContextCompat.getColor(this, R.color.tab_favorite)))
                .addItem(BottomNavigationItem(R.drawable.ic_history_24dp, R.string.tab_history)
                        .setActiveColor(ContextCompat.getColor(this, R.color.tab_history)))
                .initialise()

        bottomNavigationBarView.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
                // No-impl
            }

            override fun onTabUnselected(position: Int) {
                // No-impl
            }

            override fun onTabSelected(position: Int) {
                when (position) {
                    0 -> viewModel.navigateTabTo(NavigationTabItem.EXPLORE)
                    1 -> viewModel.navigateTabTo(NavigationTabItem.FAVOURITE)
                    2 -> viewModel.navigateTabTo(NavigationTabItem.HISTORY)
                }
            }
        })

        viewModel.navigation().observe(this, Observer { navigateTo(it!!) })
        viewModel.navigationTab().observe(this, Observer { navigateTabTo(it!!) })
        viewModel.searchNavigation().observe(this, Observer { navigateTo(it!!) })
    }

    private fun initSearch() {
        floatingSearchView.apply {
            icon = DrawerArrowDrawable(this@LingoHomeActivity)
            setOnIconClickListener { viewModel.navigationIconClicked() }
            setOnSearchListener { query -> viewModel.submitQuery(query.toString()) }
            setOnSearchFocusChangedListener { focused ->
                viewModel.requestFocusGain(focused)
                if (focused) {
                    this@LingoHomeActivity.appBarView.setExpanded(true, true)
                }
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_clear -> viewModel.clearIconClicked()
                    R.id.menu_tts -> viewModel.textToSpeechIconClicked()
                }
                true
            }
            addTextChangedListener(object : TextWatcherAdapter() {
                override fun afterTextChanged(query: Editable) {
                    viewModel.updateQuery(query.toString())
                }
            })
            adapter = LingoSearchSuggestionsAdapter()
        }
    }

    private fun showSearchProgress(shouldShow: Boolean) {
        floatingSearchView.menu.findItem(R.id.menu_progress)?.isVisible = shouldShow
    }

    private fun showTextToSpeechIcon(shouldShow: Boolean) {
        floatingSearchView.menu.findItem(R.id.menu_tts)?.isVisible = shouldShow
    }

    private fun showClearButton(shouldShow: Boolean) {
        floatingSearchView.menu.findItem(R.id.menu_clear)?.isVisible = shouldShow
    }

    private fun navigateTo(navigationItem: NavigationItem) {
        L.v("navigateTo: navigationItem = $navigationItem")
        when (navigationItem) {
            NavigationItem.BACKWARD -> navigator.goBack()
        }
    }

    private fun navigateTabTo(navigationTabItem: LingoHomeActivityViewModel.NavigationTabItem) {
        L.v("navigateTabTo: navigationTabItem = $navigationTabItem")
        when (navigationTabItem) {
            NavigationTabItem.EXPLORE -> {
                navigator.showExploreSubScreen()
                bottomNavigationBarView.selectTab(0, false)
            }
            NavigationTabItem.FAVOURITE -> {
                navigator.showExploreSubScreen()
                bottomNavigationBarView.selectTab(1, false)
            }
            NavigationTabItem.HISTORY -> {
                navigator.showExploreSubScreen()
                bottomNavigationBarView.selectTab(2, false)
            }
        }
    }

    private fun navigateTo(navigationItem: SearchViewModel.SearchNavigationItem) {
        L.v("navigateTo: navigationItem = $navigationItem")
        when (navigationItem) {
            SearchNavigationItem.DRAWER -> toast("open Drawer please")
            SearchNavigationItem.TEXT_TO_SPEECH -> navigator.showTextToSpeech("What are you looking for?")
        }
    }

    override fun onBackPressed() {
        viewModel.navigateTo(NavigationItem.BACKWARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                viewModel.handleTextToSpeechResult(textToSpeechResultMapper.transform(resultCode, data))
            }
        }
    }

    override fun viewModelType() = LingoHomeActivityViewModel::class.java

}
