package org.brainail.everboxing.lingo.ui.home

import android.os.Handler
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.ui.home.details.LingoSearchResultDetailsFragment
import org.brainail.everboxing.lingo.ui.home.explore.LingoSearchFragment
import org.brainail.everboxing.lingo.widget.AppCompatBottomAppBar

class LingoHomeActivityViewRenderer(
        private val activity: AppCompatActivity,
        private val appBarLayout: AppBarLayout,
        private val bottomAppBar: AppCompatBottomAppBar,
        private val actionButton: FloatingActionButton): LifecycleObserver {

    private val renderPageSwitchingAction = {
        val fragment = activity.supportFragmentManager.findFragmentById(R.id.containerView)
        renderPageSwitching(fragment?.tag)
    }

    init {
        activity.lifecycle.addObserver(this)
    }

    fun init() {
        if (activity.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            start()
        }
    }

    fun selectHomeMenuItem(@IdRes menuItemId: Int) {
        val menu = bottomAppBar.menu
        when (menuItemId) {
            R.id.menu_home_explore -> {
                menu.findItem(R.id.menu_home_explore)?.setIcon(R.drawable.ic_baseline_view_agenda_24dp)
                menu.findItem(R.id.menu_home_favorite)?.setIcon(R.drawable.ic_twotone_favorite_24dp)
                menu.findItem(R.id.menu_home_history)?.setIcon(R.drawable.ic_twotone_watch_later_24dp)
            }
            R.id.menu_home_favorite -> {
                menu.findItem(R.id.menu_home_explore)?.setIcon(R.drawable.ic_twotone_view_agenda_24dp)
                menu.findItem(R.id.menu_home_favorite)?.setIcon(R.drawable.ic_baseline_favorite_24dp)
                menu.findItem(R.id.menu_home_history)?.setIcon(R.drawable.ic_twotone_watch_later_24dp)
            }
            R.id.menu_home_history -> {
                menu.findItem(R.id.menu_home_explore)?.setIcon(R.drawable.ic_twotone_view_agenda_24dp)
                menu.findItem(R.id.menu_home_favorite)?.setIcon(R.drawable.ic_twotone_favorite_24dp)
                menu.findItem(R.id.menu_home_history)?.setIcon(R.drawable.ic_baseline_watch_later_24dp)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected fun start() {
        startListenToBackStack()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun stop() {
        stopListenToBackStack()
    }

    private fun startListenToBackStack() {
        val fragmentManager = activity.supportFragmentManager
        fragmentManager.removeOnBackStackChangedListener(renderPageSwitchingAction)
        fragmentManager.addOnBackStackChangedListener(renderPageSwitchingAction)
        if (fragmentManager.backStackEntryCount > 0) {
            val topIndex = fragmentManager.backStackEntryCount - 1
            renderPageSwitching(fragmentManager.getBackStackEntryAt(topIndex).name)
        }
    }

    private fun stopListenToBackStack() {
        activity.supportFragmentManager.removeOnBackStackChangedListener(renderPageSwitchingAction)
    }

    private fun renderPageSwitching(fragmentTag: String?) {
        // enable mode
        when (fragmentTag) {
            LingoSearchResultDetailsFragment.layoutTag -> enableSearchResultDetailsPageMode()
            else -> enableLingoHomePageMode()
        }

        // render menu
        when (fragmentTag) {
            LingoSearchFragment.layoutTag -> selectHomeMenuItem(R.id.menu_home_explore)
        }
    }

    private fun enableSearchResultDetailsPageMode() {
        appBarLayout.setExpanded(false)
        bottomAppBar.show()
        bottomAppBar.replaceMenu(R.menu.menu_details_bottom_bar)
        Handler().postDelayed({ bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER }, 200)
        actionButton.id = R.id.detailsActionButtonView
        actionButton.setImageResource(R.drawable.ic_twotone_favorite_24dp)
    }

    private fun enableLingoHomePageMode() {
        appBarLayout.setExpanded(true)
        bottomAppBar.replaceMenu(R.menu.menu_home_bottom_bar)
        Handler().postDelayed({ bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END }, 200)
        actionButton.id = R.id.homeActionButtonView
        actionButton.setImageResource(R.drawable.ic_search_black_24dp)
    }
}