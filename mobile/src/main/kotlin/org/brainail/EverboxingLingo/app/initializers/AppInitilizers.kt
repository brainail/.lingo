package org.brainail.EverboxingLingo.app.initializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}