package org.brainail.everboxing.lingo.tasks.initializers

import android.app.Application
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.everboxing.lingo.base.tasks.SyncTasks
import org.brainail.everboxing.lingo.domain.settings.SyncSettings
import org.brainail.logger.L

class PreinstalledUrbanDataInitializer(
        private val pathToData: String,
        private val syncSettings: SyncSettings,
        private val syncTasks: SyncTasks) : AppInitializer {

    override fun init(application: Application) {
        if (!syncSettings.isPreinstalledUrbanDataInitialized) {
            syncTasks.installUrbanServiceData(pathToData)
        }
    }
}
