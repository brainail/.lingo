package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.text.Editable
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.ui.BaseActivity
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
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit private var viewModel: LingoHomeActivityViewModel
    lateinit private var searchViewModel: SearchViewModel

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
        floatingSearchView.menu.findItem(R.id.menu_tts)?.isVisible = navigator.canShowTextToSpeech()
        floatingSearchView.showLogo(true)
        floatingSearchView.showIcon(shouldShowSearchNavigationIcon())
        floatingSearchView.icon = DrawerArrowDrawable(this)
        floatingSearchView.setOnIconClickListener {
            when (floatingSearchView.isActivated) {
                true -> floatingSearchView.isActivated = false
                else -> toast("open Drawer please")
            }
        }
        floatingSearchView.setOnSearchListener { query ->
            floatingSearchView.isActivated = false
            searchForResults(query.toString())
        }
        floatingSearchView.setOnSearchFocusChangedListener { focused ->
            showClearButton(focused && !floatingSearchView.text.isEmpty())
            if (!focused) {
                showProgress(false)
            } else {
                appBarView.setExpanded(true, true)
            }
            floatingSearchView.showLogo(!focused && floatingSearchView.text.isEmpty())
            floatingSearchView.showIcon(focused || shouldShowSearchNavigationIcon())
        }
        floatingSearchView.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_clear -> floatingSearchView.clearText()
                R.id.menu_tts -> showTextToSpeech()
            }
            true
        }
        floatingSearchView.addTextChangedListener(object: TextWatcherAdapter() {
            override fun afterTextChanged(query: Editable) {
                showClearButton(query.isNotEmpty() && floatingSearchView.isActivated)
                floatingSearchView.showLogo(!floatingSearchView.isActivated && floatingSearchView.text.isEmpty())
                if (floatingSearchView.isActivated) {
                    searchForSuggestions(query.toString().trim())
                }
            }
        })

        if (null == savedInstanceState) {
            showClearButton(false)
        }
    }

    private fun shouldShowSearchNavigationIcon(): Boolean = true

    private fun showProgress(shouldShow: Boolean) {
        // floatingSearchView.menu.findItem(R.id.menu_progress)?.isVisible = shouldShow
    }

    private fun searchForSuggestions(query: String) {
        showProgress(floatingSearchView.isActivated) // it's should be decided by viewmodel, view is stupid! :(
        searchViewModel.onQueryTextChange(query)
    }

    private fun showClearButton(shouldShow: Boolean) {
        floatingSearchView.menu.findItem(R.id.menu_clear)?.isVisible = shouldShow
    }

    private fun showTextToSpeech() {
        navigator.showTextToSpeech("What are you looking for?")
    }

    private fun searchForResults(query: String) {
        searchViewModel.onQueryTextSubmit(query)
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

    private fun goBack() {
        return when (floatingSearchView.isActivated) {
            true -> floatingSearchView.isActivated = false
            else -> navigator.goBack()
        }
    }

    override fun onBackPressed() {
        viewModel.navigateTo(BACKWARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            LingoHomeActivityNavigator.REQ_CODE_SPEECH_INPUT -> handleSpeechInputActivityResult(resultCode, data)
        }
    }

    private fun handleSpeechInputActivityResult(activityResultCode: Int, activityResultData: Intent?) {
        if (activityResultCode == AppCompatActivity.RESULT_OK) {
            activityResultData?.run {
                val result = getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                floatingSearchView.text = result[0]
                floatingSearchView.setSelection(floatingSearchView.text.length)
                floatingSearchView.isActivated = true
            }
        }
    }

}
