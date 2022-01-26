package com.graphybyte.githubtrendingrepo.ui.list

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ScheduleAPICallWorker constructor(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    @Inject
    lateinit var githubRepoRepository: GithubRepoRepository

    override fun doWork(): Result {
        Timber.d("API Calling in worker Manger..")
        CoroutineScope(Dispatchers.IO).launch {
            githubRepoRepository?.let {
                it.getGithubRepoBackground()
            }
        }

        return Result.success()
    }

}