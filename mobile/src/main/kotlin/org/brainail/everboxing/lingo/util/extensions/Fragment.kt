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
import androidx.navigation.findNavController
import androidx.navigation.navOptions

inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().transaction().commit()
}

inline fun AppCompatActivity.getNavigationTopFragment(@IdRes navFragmentId: Int): Fragment? {
    val navFragment = supportFragmentManager.findFragmentById(navFragmentId)
    return navFragment?.childFragmentManager?.findFragmentById(navFragmentId)
}

inline fun AppCompatActivity.navigateFromStart(@IdRes navFragmentId: Int, @IdRes destinationId: Int) {
    val navController = findNavController(navFragmentId)
    if (navController.currentDestination?.id != destinationId) {
        navController.navigate(
            destinationId,
            null,
            navOptions { popUpTo(navController.graph.startDestination, popUpToBuilder = { inclusive = true }) }
        )
    }
}

inline fun Fragment.drawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(requireContext(), drawable)!!
inline fun Fragment.color(@ColorRes color: Int) = ContextCompat.getColor(requireContext(), color)
inline fun Fragment.pixelOffset(@DimenRes dimen: Int) = resources.getDimensionPixelOffset(dimen)
inline fun Fragment.pixelSize(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)
