@file:JvmName("FragmentUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.everboxing.lingo.util.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().transaction().commit()
}

inline fun <reified VM : ViewModel> Fragment.getViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getActivityViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

inline fun AppCompatActivity.getNavigationTopFragment(@IdRes navFragmentId: Int): Fragment? {
    val navFragment = supportFragmentManager.findFragmentById(navFragmentId)
    return navFragment?.childFragmentManager?.findFragmentById(navFragmentId)
}