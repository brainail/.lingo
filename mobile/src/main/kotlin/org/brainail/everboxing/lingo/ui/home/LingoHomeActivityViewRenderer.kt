package org.brainail.everboxing.lingo.ui.home

import android.os.Handler
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.widget.AppCompatBottomAppBar

class LingoHomeActivityViewRenderer(
        private val activity: AppCompatActivity,
        private val appBarLayout: AppBarLayout,
        private val bottomAppBar: AppCompatBottomAppBar,
        private val actionButton: FloatingActionButton,
        private val uiExecutor: Handler): LifecycleObserver {

    private val alignFabCenterAction = {
        if (bottomAppBar.fabAlignmentMode != BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) { // avoid blinking
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        }
    }

    private val alignFabEndAction = {
        if (bottomAppBar.fabAlignmentMode != BottomAppBar.FAB_ALIGNMENT_MODE_END) { // avoid blinking
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        }
    }

    private val renderPageSwitchingAction = { _: NavController, navDestination: NavDestination ->
        renderPageSwitching(navDestination.id)
    }

    fun init() {
        activity.lifecycle.addObserver(this)
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
        val navController = activity.findNavController(R.id.lingoHomeNavigationFragment)
        navController.removeOnNavigatedListener(renderPageSwitchingAction)
        navController.addOnNavigatedListener(renderPageSwitchingAction)
    }

    private fun stopListenToBackStack() {
        val navController = activity.findNavController(R.id.lingoHomeNavigationFragment)
        navController.removeOnNavigatedListener(renderPageSwitchingAction)
    }

    private fun renderPageSwitching(@IdRes fragmentDestinationId: Int?) {
        when (fragmentDestinationId) {
            R.id.wordDetailsPageDestination -> enableWordDetailsPageMode()
            else -> enableLingoHomePageMode()
        }
        when (fragmentDestinationId) {
            R.id.explorePageDestination -> selectHomeMenuItem(R.id.menu_home_explore)
        }
    }

    private fun enableWordDetailsPageMode() {
        appBarLayout.setExpanded(false)
        if (null == bottomAppBar.menu.findItem(R.id.menu_details_share)) { // avoid blinking
            bottomAppBar.replaceMenu(R.menu.menu_details_bottom_bar)
        }
        bottomAppBar.show()
        scheduleAlignActionButtonAction(alignFabCenterAction)
        actionButton.id = R.id.detailsActionButtonView
        actionButton.setImageResource(R.drawable.ic_twotone_favorite_24dp)
    }

    private fun enableLingoHomePageMode() {
        appBarLayout.setExpanded(true)
        if (null == bottomAppBar.menu.findItem(R.id.menu_home_explore)) { // avoid blinking
            bottomAppBar.replaceMenu(R.menu.menu_home_bottom_bar)
        }
        scheduleAlignActionButtonAction(alignFabEndAction)
        actionButton.id = R.id.homeActionButtonView
        actionButton.setImageResource(R.drawable.ic_search_black_24dp)
    }

    private fun scheduleAlignActionButtonAction(action: () -> Unit) {
        uiExecutor.removeCallbacks(alignFabCenterAction)
        uiExecutor.removeCallbacks(alignFabEndAction)
        uiExecutor.postDelayed(action, 200)
    }
}