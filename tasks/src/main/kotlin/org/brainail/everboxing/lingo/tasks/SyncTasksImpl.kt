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

package org.brainail.everboxing.lingo.tasks

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import org.brainail.everboxing.lingo.base.app.tasks.SyncTasks
import javax.inject.Inject

class SyncTasksImpl @Inject constructor(private val workManager: WorkManager) : SyncTasks {
    override fun installUrbanServiceData(pathToData: String) {
        val request = OneTimeWorkRequest.Builder(InstallUrbanServiceDataTask::class.java)
            .addTag(InstallUrbanServiceDataTask.workerTag)
            .setInputData(InstallUrbanServiceDataTask.makeInputData(pathToData))
            .build()
        workManager.enqueue(request)
    }
}
