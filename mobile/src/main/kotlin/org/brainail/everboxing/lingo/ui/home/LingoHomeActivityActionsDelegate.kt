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

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.util.MenuItemClickHandler
import org.brainail.everboxing.lingo.util.ViewClickHandler
import org.brainail.everboxing.lingo.util.extensions.getNavigationTopFragment

/**
 * Acts on different actions from [LingoHomeActivity]
 */
class LingoHomeActivityActionsDelegate(private val activity: AppCompatActivity) {
    fun handleMenuItemClick(menuItem: MenuItem): Boolean {
        val fragment = activity.getNavigationTopFragment(R.id.lingoHomeNavigationFragment)
        return (fragment as? MenuItemClickHandler)?.handleMenuItemClick(menuItem) == true
    }

    fun handleViewClick(view: View): Boolean {
        val fragment = activity.getNavigationTopFragment(R.id.lingoHomeNavigationFragment)
        return (fragment as? ViewClickHandler)?.handleViewClick(view) == true
    }
}
