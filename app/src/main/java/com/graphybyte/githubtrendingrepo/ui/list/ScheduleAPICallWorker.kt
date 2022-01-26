package com.graphybyte.githubtrendingrepo.ui.list

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import javax.inject.Inject

class ScheduleAPICallWorker constructor(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {


    @Inject
    lateinit var viewmodel: GithubRepoViewModel

    override fun doWork(): Result {
        viewmodel?.let {
            it.pageNumber = it.pageNumber + 1
            it.getGithubRepoList()
            return Result.success()
        }
        return Result.failure()
    }

}