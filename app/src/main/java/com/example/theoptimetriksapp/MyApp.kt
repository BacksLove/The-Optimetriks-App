package com.example.theoptimetriksapp

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.theoptimetriksapp.constants.Constants
import com.example.theoptimetriksapp.database.AppDatabase
import com.example.theoptimetriksapp.repository.UserRepository

class MyApp : Application() {

    companion object {
        lateinit var db: AppDatabase
        lateinit var userRepository: UserRepository
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE)
        db = Room.databaseBuilder(this, AppDatabase::class.java, Constants.DATABASE_NAME).fallbackToDestructiveMigration().build()

        userRepository = UserRepository()

        // Au lancement de l'application, je lance une synchronisation des données de l'API dans la BDD locale
        userRepository.dailySyncUser()
    }

}