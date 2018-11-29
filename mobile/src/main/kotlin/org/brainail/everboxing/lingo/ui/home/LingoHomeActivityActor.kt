package org.brainail.everboxing.lingo.ui.home

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.util.MenuItemClickHandler
import org.brainail.everboxing.lingo.util.ViewClickHandler
import org.brainail.everboxing.lingo.util.extensions.getNavigationTopFragment

/**
 * Acts on different actions from [LingoHomeActivity]
 */
class LingoHomeActivityActor(private val activity: AppCompatActivity) {
    fun handleMenuItemClick(@IdRes menuId: Int): Boolean {
        val fragment = activity.getNavigationTopFragment(R.id.lingoHomeNavigationFragment)
        return (fragment as? MenuItemClickHandler)?.handleMenuItemClick(menuId) == true
    }

    fun handleViewClick(@IdRes viewId: Int): Boolean {
        val fragment = activity.getNavigationTopFragment(R.id.lingoHomeNavigationFragment)
        return (fragment as? ViewClickHandler)?.handleViewClick(viewId) == true
    }
}