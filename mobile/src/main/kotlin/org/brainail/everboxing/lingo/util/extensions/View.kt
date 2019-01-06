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

@file:JvmName("ViewUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.everboxing.lingo.util.extensions

import android.view.View
import com.google.android.material.appbar.AppBarLayout

private const val NO_FOCUS_SEARCH_SCROLL_FLAGS =
    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
        AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP or
        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS

fun View.lockInAppBar(locked: Boolean) {
    // post it to get rid of flickering effect
    post {
        val childLp = layoutParams as AppBarLayout.LayoutParams
        val newScrollFlags = if (locked) 0 else NO_FOCUS_SEARCH_SCROLL_FLAGS
        childLp.takeIf { it.scrollFlags != newScrollFlags }?.apply {
            scrollFlags = newScrollFlags
            layoutParams = this // important in order to have the proper effect
        }
    }
}
