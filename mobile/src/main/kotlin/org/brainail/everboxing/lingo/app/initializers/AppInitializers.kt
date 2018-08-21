package org.brainail.everboxing.lingo.app.initializers

import android.app.Application

class AppInitializers(private vararg val initializers: AppInitializer) : AppInitializer {
    override fun init(application: Application) {
        initializers.forEach { it.init(application) }
    }
}