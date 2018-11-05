package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import org.brainail.everboxing.lingo.base.app.AppInitializer
import javax.inject.Inject

class AppInitializers @Inject constructor(
        private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) : AppInitializer {

    override fun init(application: Application) {
        initializers.forEach { it.init(application) }
    }
}