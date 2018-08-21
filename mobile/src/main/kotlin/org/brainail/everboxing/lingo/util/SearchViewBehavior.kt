package org.brainail.everboxing.lingo.util

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

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingSearchView, dependency: View): Boolean {
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