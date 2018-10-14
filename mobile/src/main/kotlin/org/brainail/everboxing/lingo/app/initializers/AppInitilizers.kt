package org.brainail.everboxing.lingo.app.initializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}