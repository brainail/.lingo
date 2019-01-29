/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.di.tasks

import androidx.work.Worker
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.base.app.tasks.SyncTasks
import org.brainail.everboxing.lingo.tasks.InstallUrbanServiceDataTask
import org.brainail.everboxing.lingo.tasks.SyncTasksImpl
import org.brainail.everboxing.lingo.tasks.di.WorkerKey

@Module(
    includes = [JobsModule::class],
    subcomponents = [InstallUrbanServiceDataTaskSubcomponent::class]
)
abstract class JobsCreator {
    @Binds
    @IntoMap
    @WorkerKey(InstallUrbanServiceDataTask::class)
    abstract fun bindInstallUrbanServiceDataTask(
        builder: InstallUrbanServiceDataTaskSubcomponent.Builder
    ): AndroidInjector.Factory<out Worker>

    @Binds
    abstract fun bindSyncTasks(syncTasks: SyncTasksImpl): SyncTasks
}
