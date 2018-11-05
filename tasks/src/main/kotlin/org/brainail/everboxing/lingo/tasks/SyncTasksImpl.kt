package org.brainail.everboxing.lingo.tasks

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import org.brainail.everboxing.lingo.base.tasks.SyncTasks
import javax.inject.Inject

class SyncTasksImpl @Inject constructor(private val workManager: WorkManager) :
        SyncTasks {
    override fun installUrbanServiceData(pathToData: String) {
        val request = OneTimeWorkRequest.Builder(InstallUrbanServiceDataTask::class.java)
                .addTag(InstallUrbanServiceDataTask.workerTag)
                .setInputData(InstallUrbanServiceDataTask.makeInputData(pathToData))
                .build()
        workManager.enqueue(request)
    }
}