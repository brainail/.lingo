package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.text.Editable
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.extensions.strim
import org.brainail.EverboxingLingo.mapper.TextToSpeechResultMapper
import org.brainail.EverboxingLingo.model.TextToSpeechResult
import org.brainail.EverboxingLingo.ui.BaseActivity
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityNavigator.Companion.REQ_CODE_SPEECH_INPUT
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.BACKWARD
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.EXPLORE
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.FAVOURITE
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.HISTORY
import org.brainail.EverboxingLingo.util.TextWatcherAdapter
import org.brainail.logger.L
import org.jetbrains.anko.toast
import javax.inject.Inject

class LingoHomeActivity : BaseActivity() {

    @Inject
    lateinit var navigator: LingoHomeActivityNavigator

    @Inject
    internal lateinit var textToSpeechResultMapper: TextToSpeechResultMapper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LingoHomeActivityViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lingo_home)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LingoHomeActivityViewModel::class.java)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)

        initNavigation()
        initSearch(savedInstanceState)
    }

    private fun initNavigation() {
        bottomNavigationBarView.setOnTabSelectListener({ tabId ->
            when (tabId) {
                R.id.tab_explore -> viewModel.navigateTo(EXPLORE)
                R.id.tab_favourite -> viewModel.navigateTo(FAVOURITE)
                R.id.tab_history -> viewModel.navigateTo(HISTORY)
            }
        }, false)

        viewModel.navigation.observe(this, Observer { navigateTo(it!!) })
    }

    private fun initSearch(savedInstanceState: Bundle?) {
        floatingSearchView.apply {
            menu.findItem(R.id.menu_tts)?.isVisible = navigator.canShowTextToSpeech()
            icon = DrawerArrowDrawable(this@LingoHomeActivity)
            showLogo(true)
            setOnIconClickListener {
                when (isActivated) {
                    true -> isActivated = false
                    else -> toast("open Drawer please")
                }
            }
            setOnSearchListener { query ->
                isActivated = false
                searchViewModel.submitQuery(query.strim())
            }
            setOnSearchFocusChangedListener { focused ->
                showClearButton(focused && !text.isEmpty())
                if (!focused) {
                    showProgress(false)
                } else {
                    this@LingoHomeActivity.appBarView.setExpanded(true, true)
                }
                showLogo(!focused && text.isEmpty())
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_clear -> clearText()
                    R.id.menu_tts -> navigator.showTextToSpeech("What are you looking for?")
                }
                true
            }
            addTextChangedListener(object: TextWatcherAdapter() {
                override fun afterTextChanged(query: Editable) {
                    showClearButton(query.isNotEmpty() && isActivated)
                    showLogo(!isActivated && text.isEmpty())
                    showProgress(isActivated)
                    if (isActivated) {
                        searchViewModel.updateQuery(query.strim())
                    }
                }
            })
        }

        if (null == savedInstanceState) {
            showClearButton(false)
        }
    }

    private fun showProgress(shouldShow: Boolean) {
        // floatingSearchView.menu.findItem(R.id.menu_progress)?.isVisible = shouldShow
    }

    private fun showClearButton(shouldShow: Boolean) {
        floatingSearchView.menu.findItem(R.id.menu_clear)?.isVisible = shouldShow
    }

    private fun navigateTo(navigationItem: NavigationItem) {
        L.v("navigateTo: navigationItem = $navigationItem")
        when (navigationItem) {
            EXPLORE -> navigator.showExploreSubScreen()
            FAVOURITE -> navigator.showExploreSubScreen()
            HISTORY -> navigator.showExploreSubScreen()
            BACKWARD -> goBack()
        }
    }

    private fun goBack() = when (floatingSearchView.isActivated) {
        true -> floatingSearchView.isActivated = false
        else -> navigator.goBack()
    }

    override fun onBackPressed() {
        viewModel.navigateTo(BACKWARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                // ---> viewModel.handleTextToSpeechResult(textToSpeechResultMapper.transform(resultCode, data))
                // <--- update viewState

                val result = textToSpeechResultMapper.transform(resultCode, data)
                if (result is TextToSpeechResult.TextToSpeechSuccessfulResult) {
                    floatingSearchView.text = result.text
                    floatingSearchView.setSelection(result.text.length)
                    floatingSearchView.isActivated = true
                }
            }
        }
    }

}
