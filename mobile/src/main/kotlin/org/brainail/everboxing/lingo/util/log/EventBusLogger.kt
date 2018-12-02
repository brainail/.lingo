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

package org.brainail.everboxing.lingo.util.log

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import org.brainail.everboxing.lingo.domain.event.EventBus
import org.brainail.logger.L

class EventBusLogger(private vararg val eventBuses: EventBus<*>) {
    var enabled: Boolean = false
        set(value) {
            if (value != field) {
                field = value
                if (value) register() else unregister()
            }
        }

    private val disposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("RxSubscribeOnError")
    private fun register() {
        eventBuses.forEach { bus ->
            disposable.add(bus.observe().subscribe {
                L.tag("${javaClass.simpleName}: ${bus.name}").v("$it")
            })
        }
    }

    private fun unregister() {
        disposable.clear()
    }
}
