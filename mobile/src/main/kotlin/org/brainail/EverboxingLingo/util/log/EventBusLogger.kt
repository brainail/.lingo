package org.brainail.EverboxingLingo.util.log

import io.reactivex.disposables.CompositeDisposable
import org.brainail.EverboxingLingo.domain.event.EventBus
import org.brainail.logger.L

class EventBusLogger(vararg val eventBuses: EventBus<*>) {

    var enabled: Boolean = false
        set (value) {
            if (value != field) {
                field = value
                if (value) register() else unregister()
            }
        }

    internal val disposable: CompositeDisposable = CompositeDisposable()

    private fun register() {
        eventBuses.forEach({ bus ->
            disposable.add(bus.observe().subscribe {
                L.tag("${javaClass.simpleName}: ${bus.name}").v("$it")
            })
        })
    }

    private fun unregister() {
        disposable.clear()
    }

}