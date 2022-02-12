package com.example.theoptimetriksapp.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.theoptimetriksapp.model.User

// Initialisation de la bdd locale avec Room
@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao() : UserDao

}