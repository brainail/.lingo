package org.brainail.EverboxingLingo.app.initializers

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import org.brainail.EverboxingLingo.ui.base.AppLifecycleObserver
import javax.inject.Inject

class AppLifecycleInitializer @Inject constructor(private val observer: AppLifecycleObserver) : AppInitializer {
    override fun init(application: Application) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }
}