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

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.domain.usecase.InstallUrbanServiceDataUseCase
import org.brainail.everboxing.lingo.tasks.di.AndroidWorkerInjector
import org.brainail.logger.L
import javax.inject.Inject

class InstallUrbanServiceDataTask(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @Inject
    lateinit var installUrbanServiceDataUseCase: InstallUrbanServiceDataUseCase

    override fun doWork(): Result {
        AndroidWorkerInjector.inject(this)

        val pathToData = inputData.getString(PARAM_PATH_TO_DATA)!!
        L.i("worker is running, where pathToData = $pathToData")

        installUrbanServiceDataUseCase.execute(pathToData).blockingAwait()

        return Result.success()
    }

    companion object {
        private const val PARAM_PATH_TO_DATA = "path-to-data"

        @JvmStatic
        val workerTag: String by lazyFast { "${InstallUrbanServiceDataTask::class.java.simpleName}.WorkerTag" }

        @JvmStatic
        fun makeInputData(pathToData: String) = Data.Builder()
            .putString(PARAM_PATH_TO_DATA, pathToData)
            .build()
    }
}
