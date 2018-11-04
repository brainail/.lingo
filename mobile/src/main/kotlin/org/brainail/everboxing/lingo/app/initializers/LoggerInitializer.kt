package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import org.brainail.everboxing.lingo.BuildConfig
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.logger.L
import javax.inject.Inject

class LoggerInitializer @Inject constructor(private val logTree: L.Tree) : AppInitializer {
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            L.plant(logTree)
        }
    }
}