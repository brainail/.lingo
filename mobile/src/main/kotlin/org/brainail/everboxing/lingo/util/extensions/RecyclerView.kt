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

@file:JvmName("RecyclerViewUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.everboxing.lingo.util.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.brainail.everboxing.lingo.util.SimpleAdapterDataObserver

private var maxScheduledScrollToTopGeneration = 0L

inline fun RecyclerView.scrollToTop() {
    (layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(0, 0)
}

fun RecyclerView.scrollToTopOnSubmitList() {
    val runGeneration = ++maxScheduledScrollToTopGeneration
    adapter?.registerAdapterDataObserver(object : SimpleAdapterDataObserver() {
        override fun onChanged() {
            adapter?.unregisterAdapterDataObserver(this)
            post {
                if (runGeneration == maxScheduledScrollToTopGeneration) {
                    scrollToTop()
                }
            }
        }
    })
}
