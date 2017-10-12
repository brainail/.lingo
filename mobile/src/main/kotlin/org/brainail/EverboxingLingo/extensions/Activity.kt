package org.brainail.EverboxingLingo.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.openFragment(fragmentTag: String, containerViewId: Int, fragmentCreator: () -> Fragment) {
    if (null == supportFragmentManager.findFragmentByTag(fragmentTag)) {
        supportFragmentManager.inTransaction {
            replace(containerViewId, fragmentCreator(), fragmentTag)
        }
    }
}