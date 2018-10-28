@file:JvmName("ActivityUtils")

package org.brainail.everboxing.lingo.util.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun AppCompatActivity.openFragment(
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

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(this, provider).get(VM::class.java)