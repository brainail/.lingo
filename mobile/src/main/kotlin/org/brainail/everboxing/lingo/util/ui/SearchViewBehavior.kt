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

package org.brainail.everboxing.lingo.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import org.brainail.flysearch.FloatingSearchView

class SearchViewBehavior : CoordinatorLayout.Behavior<FloatingSearchView> {

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingSearchView, dependency: View): Boolean {
        if (dependency is AppBarLayout) {
            ViewCompat.setElevation(child, ViewCompat.getElevation(dependency))
            return true
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingSearchView,
        dependency: View
    ): Boolean {

        if (dependency is AppBarLayout) {
            val layoutParams = dependency.getLayoutParams() as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior as? AppBarLayout.Behavior
            behavior?.let {
                child.translationY = it.topAndBottomOffset.toFloat()
            }
            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}
