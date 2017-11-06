package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.ui.BaseActivity
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.EXPLORE
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.FAVOURITE
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel.NavigationItem.HISTORY
import org.brainail.logger.L
import javax.inject.Inject

class LingoHomeActivity : BaseActivity() {

    @Inject
    lateinit var navigator: LingoHomeActivityNavigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit private var viewModel: LingoHomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lingo_home)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LingoHomeActivityViewModel::class.java)

        bottomNavigationBarView.setOnTabSelectListener({ tabId ->
            when (tabId) {
                R.id.tab_explore -> viewModel.navigate(EXPLORE)
                R.id.tab_favourite -> viewModel.navigate(FAVOURITE)
                R.id.tab_history -> viewModel.navigate(HISTORY)
            }
        }, false)

        viewModel.navigationLiveData.observe(this, Observer {
            showNavigationItem(it!!)
        })
    }

    private fun showNavigationItem(navigationItem: NavigationItem) {
        L.v("showNavigationItem: navigationItem = $navigationItem")
        when (navigationItem) {
            EXPLORE -> navigator.showExploreSubScreen()
            FAVOURITE -> navigator.showExploreSubScreen()
            HISTORY -> navigator.showExploreSubScreen()
        }
    }

    override fun onBackPressed() {
        if (!navigator.goBack()) {
            supportFinishAfterTransition()
        }
    }

}
