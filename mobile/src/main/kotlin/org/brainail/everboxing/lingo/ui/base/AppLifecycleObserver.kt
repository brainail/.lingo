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

package org.brainail.everboxing.lingo.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.brainail.everboxing.lingo.domain.event.EventBus
import org.brainail.everboxing.lingo.domain.event.GlobalEvents
import org.brainail.logger.L

class AppLifecycleObserver(private val globalBus: EventBus<GlobalEvents>) : LifecycleObserver {
    private var isOnForeground: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                notifyUiStateChanged(value)
            }
        }

    init {
        notifyUiStateChanged(false)
    }

    private fun notifyUiStateChanged(isOnForeground: Boolean) {
        globalBus.postSticky(GlobalEvents.UiStateChanged(isOnForeground))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        L.v("onStart")
        isOnForeground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        L.v("onStop")
        isOnForeground = false
    }
}
