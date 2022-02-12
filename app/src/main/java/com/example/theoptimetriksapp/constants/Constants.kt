package com.example.theoptimetriksapp.constants

class Constants {
    companion object {
        // URL de l'API
        const val USER_URL = "https://reqres.in/api/users"

        const val DATABASE_NAME = "OptimetriksDB"

        const val REQUEST_CODE_CAMERA = 200
        const val PERMISSION_CODE = 100

        // Nom des clés des sharedPreferences pour les données du Profile
        const val PREFERENCE_NAME = "Optimetriks SharedPreference"
        const val PROFILE_NAME_KEY = "Profile_Name"
        const val PROFILE_DESCRIPTION_KEY = "Profile_Description"
        const val PROFILE_IMAGE_KEY = "Profile_Image_Path"
    }
}