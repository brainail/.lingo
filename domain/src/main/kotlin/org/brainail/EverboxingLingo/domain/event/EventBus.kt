package org.brainail.EverboxingLingo.domain.event

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