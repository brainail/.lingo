package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lingo_home.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.ui.BaseActivity
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
            L.v("onTabSelected: tabId = $tabId")
            navigator.showSearchSubScreen()
        }, null == savedInstanceState)
    }

    override fun onBackPressed() {
        if (!navigator.goBack()) {
            supportFinishAfterTransition()
        }
    }

}
