/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.ui.home

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.util.extensions.lockInAppBar
import org.brainail.everboxing.lingo.widget.AppCompatBottomAppBar

/**
 * Renders some main __UI__ components which have any complicated logic from [LingoHomeActivity]
 */
class LingoHomeActivityViewRenderer(
    private val activity: AppCompatActivity,
    private val appBarView: AppBarLayout,
    private val bottomAppBarView: AppCompatBottomAppBar,
    private val actionButtonView: FloatingActionButton,
    private val toolbarUnderlayView: View,
    private val floatingSearchView: View
) {

    private val uiExecutor: Handler = Handler(Looper.getMainLooper())

    internal open inner class BackStackLifecycleObserver : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        protected fun start() {
            startListenToBackStack()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        protected fun stop() {
            stopListenToBackStack()
        }
    }

    private val alignFabCenterAction = {
        if (bottomAppBarView.fabAlignmentMode != BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) { // avoid blinking
            bottomAppBarView.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        }
    }

    private val alignFabEndAction = {
        if (bottomAppBarView.fabAlignmentMode != BottomAppBar.FAB_ALIGNMENT_MODE_END) { // avoid blinking
            bottomAppBarView.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        }
    }

    private val renderPageSwitchingAction = NavController.OnDestinationChangedListener { _, destination, _ ->
        renderPageSwitching(destination.id)
    }

    fun init() {
        activity.lifecycle.addObserver(BackStackLifecycleObserver())
    }

    private fun selectHomeMenuItem(@IdRes menuItemId: Int) {
        val menu = bottomAppBarView.menu
        when (menuItemId) {
            R.id.menuHomeExploreItem -> {
                menu.findItem(R.id.menuHomeExploreItem)?.setIcon(R.drawable.ic_baseline_view_agenda_24dp)
                menu.findItem(R.id.menuHomeFavoriteItem)?.setIcon(R.drawable.ic_twotone_favorite_24dp)
                menu.findItem(R.id.menuHomeHistoryItem)?.setIcon(R.drawable.ic_twotone_watch_later_24dp)
            }
            R.id.menuHomeFavoriteItem -> {
                menu.findItem(R.id.menuHomeExploreItem)?.setIcon(R.drawable.ic_twotone_view_agenda_24dp)
                menu.findItem(R.id.menuHomeFavoriteItem)?.setIcon(R.drawable.ic_baseline_favorite_24dp)
                menu.findItem(R.id.menuHomeHistoryItem)?.setIcon(R.drawable.ic_twotone_watch_later_24dp)
            }
            R.id.menuHomeHistoryItem -> {
                menu.findItem(R.id.menuHomeExploreItem)?.setIcon(R.drawable.ic_twotone_view_agenda_24dp)
                menu.findItem(R.id.menuHomeFavoriteItem)?.setIcon(R.drawable.ic_twotone_favorite_24dp)
                menu.findItem(R.id.menuHomeHistoryItem)?.setIcon(R.drawable.ic_baseline_watch_later_24dp)
            }
        }
    }

    private fun startListenToBackStack() {
        val navController = activity.findNavController(R.id.lingoHomeNavigationFragment)
        navController.removeOnDestinationChangedListener(renderPageSwitchingAction)
        navController.addOnDestinationChangedListener(renderPageSwitchingAction)
    }

    private fun stopListenToBackStack() {
        val navController = activity.findNavController(R.id.lingoHomeNavigationFragment)
        navController.removeOnDestinationChangedListener(renderPageSwitchingAction)
    }

    private fun renderPageSwitching(@IdRes fragmentDestinationId: Int?) {
        when (fragmentDestinationId) {
            R.id.wordDetailsPageDestination -> enableWordDetailsPageMode()
            else -> enableLingoHomePageMode()
        }

        when (fragmentDestinationId) {
            R.id.explorePageDestination -> selectHomeMenuItem(R.id.menuHomeExploreItem)
            R.id.favoritePageDestination -> selectHomeMenuItem(R.id.menuHomeFavoriteItem)
            R.id.historyPageDestination -> selectHomeMenuItem(R.id.menuHomeHistoryItem)
        }
    }

    private fun enableWordDetailsPageMode() {
        showOrHideSearch(false)
        bottomAppBarView.takeIf { null == bottomAppBarView.menu.findItem(R.id.menuDetailsShareItem) }
            ?.replaceMenu(R.menu.menu_details_bottom_bar) // avoid blinking
        bottomAppBarView.show()
        scheduleAlignActionButtonAction(alignFabCenterAction)
        actionButtonView.id = R.id.detailsActionButtonView
        actionButtonView.setImageResource(R.drawable.ic_twotone_favorite_24dp)
    }

    private fun enableLingoHomePageMode() {
        showOrHideSearch(true)
        bottomAppBarView.takeIf { null == bottomAppBarView.menu.findItem(R.id.menuHomeExploreItem) }
            ?.replaceMenu(R.menu.menu_home_bottom_bar) // avoid blinking
        scheduleAlignActionButtonAction(alignFabEndAction)
        actionButtonView.id = R.id.homeActionButtonView
        actionButtonView.setImageResource(R.drawable.ic_search_black_24dp)
    }

    private fun showOrHideSearch(show: Boolean) {
        floatingSearchView.isVisible = show
        floatingSearchView.isEnabled = show
        appBarView.updateLayoutParams { height = if (show) ViewGroup.LayoutParams.WRAP_CONTENT else 0 }
        toolbarUnderlayView.lockInAppBar(!show)
        appBarView.setExpanded(true) // always expand in order to push the current content down
    }

    private fun scheduleAlignActionButtonAction(action: () -> Unit) {
        uiExecutor.removeCallbacks(alignFabCenterAction)
        uiExecutor.removeCallbacks(alignFabEndAction)
        uiExecutor.postDelayed(action, ALIGN_ACTION_BUTTON_DELAY_MILLIS)
    }

    companion object {
        private const val ALIGN_ACTION_BUTTON_DELAY_MILLIS = 200L
    }
}
