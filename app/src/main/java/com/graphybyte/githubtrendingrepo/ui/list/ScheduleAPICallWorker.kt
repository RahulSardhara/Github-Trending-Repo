package com.graphybyte.githubtrendingrepo.ui.list

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import javax.inject.Inject

class ScheduleAPICallWorker constructor(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    @Inject
    lateinit var githubRepoRepository: GithubRepoRepository

    override fun doWork(): Result {
       githubRepoRepository?.let {
           it.getGithubRepoBackground()
           return Result.success()
       }
        return Result.failure()
    }

}