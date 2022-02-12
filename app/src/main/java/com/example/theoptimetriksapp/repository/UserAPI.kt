package com.example.theoptimetriksapp.repository


import com.example.theoptimetriksapp.MyApp
import com.example.theoptimetriksapp.constants.Constants
import com.example.theoptimetriksapp.model.UserJson
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class UserAPI {

    fun loadUser() {
        val url = Constants.USER_URL
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Echec")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string().toString()
                val gson = GsonBuilder().create()

                val myData = gson.fromJson(body, UserJson::class.java)

                MyApp.db.userDao().insertUsers(myData.userList)
            }
        })
    }

}