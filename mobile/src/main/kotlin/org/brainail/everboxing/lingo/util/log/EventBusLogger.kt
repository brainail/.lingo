package org.brainail.everboxing.lingo.util.log

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import org.brainail.everboxing.lingo.domain.event.EventBus
import org.brainail.logger.L

class EventBusLogger(private vararg val eventBuses: EventBus<*>) {
    var enabled: Boolean = false
        set (value) {
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