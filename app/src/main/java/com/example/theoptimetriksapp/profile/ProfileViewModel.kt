package com.example.theoptimetriksapp.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theoptimetriksapp.MyApp
import com.example.theoptimetriksapp.constants.Constants
import com.example.theoptimetriksapp.model.Profile


class ProfileViewModel : ViewModel() {

    var profile =  MutableLiveData<Profile>()

    fun updateProfile(profile: Profile) {
        val editor = MyApp.sharedPreferences.edit()
        editor.putString(Constants.PROFILE_NAME_KEY, profile.name)
        editor.putString(Constants.PROFILE_DESCRIPTION_KEY, profile.description)

        // Si l'utilisateur ne renseigne pas de nouvelles photos, on garde la photo précédente en mémoire
        if (profile.image.isNotEmpty())
            editor.putString(Constants.PROFILE_IMAGE_KEY, profile.image)
        editor.apply()
        this.profile.value = profile
    }

    fun getProfile() : LiveData<Profile> {
        val name = MyApp.sharedPreferences.getString(Constants.PROFILE_NAME_KEY, "")
        val description = MyApp.sharedPreferences.getString(Constants.PROFILE_DESCRIPTION_KEY, "")
        val image = MyApp.sharedPreferences.getString(Constants.PROFILE_IMAGE_KEY, "")
        val profile = Profile(name!!, description!!, image!!)
        this.profile.value = profile
        return this.profile
    }


}