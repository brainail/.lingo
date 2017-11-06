package org.brainail.EverboxingLingo.ui.home

import android.support.v7.app.AppCompatActivity
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.extensions.openFragment
import org.brainail.EverboxingLingo.navigator.Navigator
import org.brainail.EverboxingLingo.ui.search.LingoSearchFragment
import org.brainail.EverboxingLingo.util.NavigableBack

class LingoHomeActivityNavigator(private val activity: AppCompatActivity) : Navigator(activity) {
    fun closeScreen() {
        activity.finish()
    }

    fun showExploreSubScreen() {
        activity.openFragment(LingoSearchFragment.FRAGMENT_TAG, R.id.containerView) {
            LingoSearchFragment.newInstance()
        }
    }

    fun goBack(): Boolean {
        val fragment = activity.supportFragmentManager.findFragmentById(R.id.containerView)
        return (fragment as? NavigableBack)?.goBack() ?: false
    }
}