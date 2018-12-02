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

package org.brainail.everboxing.lingo.domain.event

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class EventBus<T : Any>(val name: String) {
    private val subject: Subject<T> = PublishSubject.create()
    private val stickyEvents: MutableMap<Class<T>, T> = ConcurrentHashMap()

    fun post(event: T) {
        subject.onNext(event)
    }

    fun postSticky(event: T) {
        stickyEvents.put(event.javaClass, event)
        subject.onNext(event)
    }

    fun removeSticky(klass: KClass<out T>) {
        stickyEvents.remove(klass.java)
    }

    fun observe(): Observable<T> = subject.mergeWith(Observable.fromIterable(stickyEvents.values))
}
