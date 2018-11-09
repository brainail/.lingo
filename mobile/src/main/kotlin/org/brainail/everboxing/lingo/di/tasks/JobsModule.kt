package org.brainail.everboxing.lingo.di.tasks

import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.base.tasks.SyncTasks
import org.brainail.everboxing.lingo.domain.Constants.URBAN_POPULAR_WORDS_FILE_NAME
import org.brainail.everboxing.lingo.domain.settings.SyncSettings
import org.brainail.everboxing.lingo.tasks.initializers.PreinstalledUrbanDataInitializer
import javax.inject.Singleton

@Module
class JobsModule {
    @Provides
    @Singleton
    fun provideWorkManager(): WorkManager = WorkManager.getInstance()

    @Provides
    @Singleton
    fun providePreinstalledUrbanDataInitializer(syncSettings: SyncSettings, synTasks: SyncTasks)
            = PreinstalledUrbanDataInitializer(URBAN_POPULAR_WORDS_FILE_NAME, syncSettings, synTasks)
}
