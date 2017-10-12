package org.brainail.EverboxingLingo.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import org.brainail.EverboxingLingo.domain.event.EventBus
import org.brainail.EverboxingLingo.domain.event.GlobalEvents
import org.brainail.logger.L

class AppLifecycleObserver(private val globalBus: EventBus<GlobalEvents>) : LifecycleObserver {

    private var isOnForeground: Boolean = false
        set (value) {
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