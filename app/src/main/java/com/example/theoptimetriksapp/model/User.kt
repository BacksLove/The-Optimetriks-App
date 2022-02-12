package com.example.theoptimetriksapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


// Classes qui servent pour le parsing du JSON et pour la bdd locale Room

data class UserJson (
        @SerializedName("data")
        val userList: List<User>
) : Serializable

@Entity
data class User (
        @PrimaryKey
        @SerializedName("id")
        @ColumnInfo(name = "id")
        val id: Int,
        @SerializedName("first_name")
        @ColumnInfo(name = "firstname")
        val firstname: String,
        @SerializedName("last_name")
        @ColumnInfo(name = "lastname")
        val lastname: String,
        @SerializedName("email")
        @ColumnInfo(name = "email")
        val email: String,
        @SerializedName("avatar")
        @ColumnInfo(name = "avatar")
        val avatar: String,
        @ColumnInfo(name = "isFavorite")
        var isFavorite: Boolean = false
) : Serializable