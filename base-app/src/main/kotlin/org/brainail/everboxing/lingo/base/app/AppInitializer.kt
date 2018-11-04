package org.brainail.everboxing.lingo.base.app

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}