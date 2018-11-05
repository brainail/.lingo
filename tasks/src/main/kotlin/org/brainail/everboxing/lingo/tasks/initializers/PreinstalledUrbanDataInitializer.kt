package org.brainail.everboxing.lingo.tasks.initializers

import android.app.Application
import org.brainail.everboxing.lingo.base.tasks.SyncTasks
import org.brainail.everboxing.lingo.base.app.AppInitializer

class PreinstalledUrbanDataInitializer(
        private val pathToData: String,
        private val syncTasks: SyncTasks) : AppInitializer {

    override fun init(application: Application) {
        syncTasks.installUrbanServiceData(pathToData)
    }
}