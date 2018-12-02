package org.brainail.everboxing.lingo.base.app

import android.app.Application

interface AppInitializer {
    fun init(application: Application)

    /**
     * Helper method to let someone be initialized before others.
     * For instance `logger` is one of the candidates.
     * By default the priority is equal to [Int.MIN_VALUE] and it's the lowest priority
     */
    fun priority() = Int.MIN_VALUE
}
