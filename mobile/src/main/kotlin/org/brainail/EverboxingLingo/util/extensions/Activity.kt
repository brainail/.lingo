@file:JvmName("ActivityUtils")

package org.brainail.EverboxingLingo.util.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

inline fun AppCompatActivity.openFragment(fragmentTag: String, containerViewId: Int, fragmentCreator: () -> Fragment) {
    if (null == supportFragmentManager.findFragmentByTag(fragmentTag)) {
        supportFragmentManager.inTransaction {
            replace(containerViewId, fragmentCreator(), fragmentTag)
        }
    }
}

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(this, provider).get(VM::class.java)