package org.brainail.everboxing.lingo.di.tasks

import androidx.work.Worker
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.base.SyncTasks
import org.brainail.everboxing.lingo.tasks.InstallUrbanServiceDataTask
import org.brainail.everboxing.lingo.tasks.SyncTasksImpl
import org.brainail.everboxing.lingo.tasks.di.WorkerKey

@Module(
        includes = [JobsModule::class],
        subcomponents = [InstallUrbanServiceDataTaskSubcomponent::class])
abstract class JobsCreator {
    @Binds
    @IntoMap
    @WorkerKey(InstallUrbanServiceDataTask::class)
    abstract fun bindInstallUrbanServiceDataTask(
            builder: InstallUrbanServiceDataTaskSubcomponent.Builder): AndroidInjector.Factory<out Worker>

    @Binds
    abstract fun bindSyncTasks(syncTasks: SyncTasksImpl): SyncTasks
}