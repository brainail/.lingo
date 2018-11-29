@file:JvmName("ActivityUtils")

package org.brainail.everboxing.lingo.util.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.openFragment(
        fragmentTag: String, containerViewId: Int, openOnTop: Boolean = false, fragmentCreator: () -> Fragment) {
    if (null == supportFragmentManager.findFragmentByTag(fragmentTag)) {
        supportFragmentManager.inTransaction {
            replace(containerViewId, fragmentCreator(), fragmentTag)
            if (openOnTop) {
                addToBackStack(fragmentTag)
            }
            this
        }
    }
}