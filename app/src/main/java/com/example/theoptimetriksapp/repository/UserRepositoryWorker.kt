package com.example.theoptimetriksapp.repository

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UserRepositoryWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        val userApi = UserAPI()

        userApi.loadUser()

        return Result.success()
    }
}