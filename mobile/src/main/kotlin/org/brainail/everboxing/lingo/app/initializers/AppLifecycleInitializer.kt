package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.everboxing.lingo.ui.base.AppLifecycleObserver
import javax.inject.Inject

class AppLifecycleInitializer @Inject constructor(private val observer: AppLifecycleObserver) : AppInitializer {
    override fun init(application: Application) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }
}