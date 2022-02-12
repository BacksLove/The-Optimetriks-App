package com.example.theoptimetriksapp.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.theoptimetriksapp.MyApp
import com.example.theoptimetriksapp.model.User

class HomeViewModel() : ViewModel() {

    var userList: LiveData<List<User>> = MyApp.db.userDao().getAllUsers()

    // Resynchronisation de la liste d'utilisateur
    fun refreshUsers() {
        MyApp.userRepository.syncUserNow()
    }

}