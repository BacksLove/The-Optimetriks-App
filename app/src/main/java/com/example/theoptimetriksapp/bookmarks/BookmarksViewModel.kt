package com.example.theoptimetriksapp.bookmarks


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.theoptimetriksapp.MyApp
import com.example.theoptimetriksapp.model.User

class BookmarksViewModel : ViewModel() {

   val favoriteUserList: LiveData<List<User>> = MyApp.db.userDao().getFavoriteUsers()

}