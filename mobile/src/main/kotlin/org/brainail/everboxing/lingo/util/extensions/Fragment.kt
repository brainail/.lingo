@file:JvmName("FragmentUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.everboxing.lingo.util.extensions

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().transaction().commit()
}

inline fun AppCompatActivity.getNavigationTopFragment(@IdRes navFragmentId: Int): Fragment? {
    val navFragment = supportFragmentManager.findFragmentById(navFragmentId)
    return navFragment?.childFragmentManager?.findFragmentById(navFragmentId)
}

inline fun Fragment.drawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(requireContext(), drawable)!!
inline fun Fragment.color(@ColorRes color: Int) = ContextCompat.getColor(requireContext(), color)
inline fun Fragment.pixelOffset(@DimenRes dimen: Int) = resources.getDimensionPixelOffset(dimen)
inline fun Fragment.pixelSize(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)

