package com.example.theoptimetriksapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.theoptimetriksapp.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM user WHERE isFavorite")
    fun getFavoriteUsers() : LiveData<List<User>>

    @Update
    fun addUserToFavorite(user: User)

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users : List<User>)

}