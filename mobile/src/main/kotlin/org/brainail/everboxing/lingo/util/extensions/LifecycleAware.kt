/*
 * Copyright 2019 Malyshev Yegor
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

@file:JvmName("LifecycleAwareUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.everboxing.lingo.util.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

fun <T, U : LifecycleOwner> lifecycleAwareLazyFast(
    lifecycleOwner: U,
    initializer: () -> T
): Lazy<T> = LifecycleAwareLazy(lifecycleOwner.lifecycle, initializer)

// marker object
private object UninitializedValue

private class LifecycleAwareLazy<out T>(
    lifecycle: Lifecycle,
    private val initializer: () -> T
) : Lazy<T>, LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun destroy() {
        lazyValue = UninitializedValue
    }

    init {
        lifecycle.addObserver(this)
    }

    private var lazyValue: Any? = UninitializedValue

    override val value: T
        get() {
            if (lazyValue === UninitializedValue) {
                lazyValue = initializer()
            }
            @Suppress("UNCHECKED_CAST")
            return lazyValue as T
        }

    override fun isInitialized() = lazyValue !== UninitializedValue

    override fun toString() = when {
        isInitialized() -> value.toString()
        else -> "LifecycleAwareLazy value not initialized yet."
    }
}
