package com.example.theoptimetriksapp.repository

import androidx.work.*
import java.util.concurrent.TimeUnit

class UserRepository {

    // Création de la contrainte de connectivité au réseau internet
    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    fun syncUserNow() {
        val work = OneTimeWorkRequestBuilder<UserRepositoryWorker>().setConstraints(constraints).build()

        WorkManager.getInstance().beginUniqueWork("syncUser", ExistingWorkPolicy.KEEP, work).enqueue()
    }

    fun dailySyncUser() {
        val work = PeriodicWorkRequestBuilder<UserRepositoryWorker>(1, TimeUnit.DAYS).setConstraints(constraints).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork("syncUserDaily", ExistingPeriodicWorkPolicy.KEEP, work)
    }

}