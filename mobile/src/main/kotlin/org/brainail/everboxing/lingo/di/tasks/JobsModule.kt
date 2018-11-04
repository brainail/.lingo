package org.brainail.everboxing.lingo.di.tasks

import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class JobsModule {
    @Provides
    @Singleton
    fun provideWorkManager(): WorkManager = WorkManager.getInstance()
}
