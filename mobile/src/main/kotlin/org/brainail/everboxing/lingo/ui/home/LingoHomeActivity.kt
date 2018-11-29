package org.brainail.everboxing.lingo.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.base.util.checkAllMatched
import org.brainail.everboxing.lingo.base.util.consume
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.mapper.TextToSpeechResultMapper
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareActivity
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityNavigator.Companion.REQ_CODE_SPEECH_INPUT
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityViewModel.NavigationItem
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityViewModel.NavigationTabItem
import org.brainail.everboxing.lingo.ui.home.search.SearchSuggestionsAdapter
import org.brainail.everboxing.lingo.ui.home.search.SearchSuggestionsAdapter.SuggestionClickListener
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel
import org.brainail.everboxing.lingo.ui.home.search.SearchViewModel.SearchNavigationItem
import org.brainail.everboxing.lingo.util.extensions.getViewModel
import org.brainail.everboxing.lingo.util.extensions.observeNonNull
import org.brainail.everboxing.lingo.util.extensions.setAfterTextChangedListener
import org.brainail.logger.L
import org.jetbrains.anko.toast
import javax.inject.Inject

class LingoHomeActivity : ViewModelAwareActivity(), SuggestionClickListener {
    @Inject
    lateinit var navigator: LingoHomeActivityNavigator
    @Inject
    lateinit var actor: LingoHomeActivityActor
    @Inject
    lateinit var textToSpeechResultMapper: TextToSpeechResultMapper

    private val screenViewModel by lazyFast {
        getViewModel<LingoHomeActivityViewModel>(viewModelFactory)
    }

    private val screenRenderer by lazyFast {
        LingoHomeActivityViewRenderer(this, appBarView, bottomAppBarView, homeActionButtonView, Handler())
    }

    private val searchViewStateRenderer by lazyFast {
        LingoHomeSearchViewStateRenderer(this, floatingSearchView, appBarView, toolbarUnderlay, bottomAppBarView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        initBottomAppBar()
        initSearchView()
        initSearchViewState()
        initViewRenderer()
    }

    override fun createPrimaryViewModels(): Array<BaseViewModel>? = arrayOf(screenViewModel)

    override fun getLayoutResourceId() = R.layout.activity_lingo_home

    private fun initViewRenderer() = screenRenderer.init()

    private fun initSearchViewState() {
        screenViewModel.searchViewState().observeNonNull(this) {
            searchViewStateRenderer.renderSearchViewState(it)
        }
    }

    private fun initBottomAppBar() {
        bottomAppBarView.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_home_explore -> consume { screenViewModel.navigateTabTo(NavigationTabItem.EXPLORE) }
                R.id.menu_home_favorite -> consume { screenViewModel.navigateTabTo(NavigationTabItem.FAVOURITE) }
                R.id.menu_home_history -> consume { screenViewModel.navigateTabTo(NavigationTabItem.HISTORY) }
                else -> actor.handleMenuItemClick(item.itemId)
            }
        }

        homeActionButtonView.setOnClickListener {
            when (homeActionButtonView.id) {
                R.id.homeActionButtonView -> screenViewModel.actionButtonClicked()
                else -> actor.handleViewClick(homeActionButtonView.id)
            }
        }
    }

    private fun initNavigation() {
        screenViewModel.navigation().observeNonNull(this) { navigateTo(it) }
        screenViewModel.navigationTab().observeNonNull(this) { navigateTabTo(it) }
        screenViewModel.searchNavigation().observeNonNull(this) { navigateTo(it) }
    }

    private fun initSearchView() {
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
            setAfterTextChangedListener { screenViewModel.updateQuery(it.toString()) }
            adapter = SearchSuggestionsAdapter(this@LingoHomeActivity)
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

    private fun navigateTo(navigationItem: SearchViewModel.SearchNavigationItem) {
        L.v("navigateTo: navigationItem = $navigationItem")
        when (navigationItem) {
            SearchNavigationItem.DRAWER -> toast("open Drawer please")
            SearchNavigationItem.TEXT_TO_SPEECH -> navigator.showTextToSpeech(R.string.home_text_to_speech_prompt)
        }.checkAllMatched
    }

    private fun navigateTabTo(navigationTabItem: LingoHomeActivityViewModel.NavigationTabItem) {
        L.v("navigateTabTo: navigationTabItem = $navigationTabItem")
        when (navigationTabItem) {
            NavigationTabItem.EXPLORE -> {
                navigator.showExplorePage()
                screenRenderer.selectHomeMenuItem(R.id.menu_home_explore)
            }
            NavigationTabItem.FAVOURITE -> {
                navigator.showExplorePage()
                screenRenderer.selectHomeMenuItem(R.id.menu_home_favorite)
            }
            NavigationTabItem.HISTORY -> {
                navigator.showExplorePage()
                screenRenderer.selectHomeMenuItem(R.id.menu_home_history)
            }
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
