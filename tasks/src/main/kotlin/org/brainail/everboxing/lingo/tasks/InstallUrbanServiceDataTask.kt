package org.brainail.everboxing.lingo.tasks

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.brainail.everboxing.lingo.domain.usecase.InstallUrbanServiceDataUseCase
import org.brainail.everboxing.lingo.tasks.di.AndroidWorkerInjector
import org.brainail.logger.L
import javax.inject.Inject

class InstallUrbanServiceDataTask(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    @Inject
    lateinit var installUrbanServiceDataUseCase: InstallUrbanServiceDataUseCase

    override fun doWork(): Result {
        AndroidWorkerInjector.inject(this)

        val pathToData = inputData.getString(PARAM_PATH_TO_DATA)!!
        L.i("worker is running, where pathToData = $pathToData")

        installUrbanServiceDataUseCase.execute(pathToData).blockingAwait()

        return Result.SUCCESS
    }

    companion object {
        const val TAG = "install-urban-service-data"

        private const val PARAM_PATH_TO_DATA = "path-to-data"

        fun makeInputData(pathToData: String) = Data.Builder()
                .putString(PARAM_PATH_TO_DATA, pathToData)
                .build()
    }
}