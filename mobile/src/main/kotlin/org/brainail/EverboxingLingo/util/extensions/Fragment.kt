@file:JvmName("FragmentUtils")

package org.brainail.EverboxingLingo.util.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().transaction().commit()
}

inline fun <reified VM : ViewModel> Fragment.getViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getActivityViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)