package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import org.brainail.everboxing.lingo.base.app.AppInitializer

class AppInitializers(private vararg val initializers: AppInitializer) : AppInitializer {
    override fun init(application: Application) {
        initializers.forEach { it.init(application) }
    }
}