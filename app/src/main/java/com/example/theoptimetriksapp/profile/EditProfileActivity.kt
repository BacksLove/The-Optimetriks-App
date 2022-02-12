package com.example.theoptimetriksapp.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.theoptimetriksapp.R
import com.example.theoptimetriksapp.constants.Constants
import com.example.theoptimetriksapp.model.Profile
import com.example.theoptimetriksapp.utilities.ImageManager
import kotlinx.android.synthetic.main.edit_user_activity.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private var imagePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.getProfile()
        profileViewModel.profile.observe(this, Observer {
            if (it.image.isNotEmpty()){
                val imageBitmap = ImageManager().decodeImage(it.image)
                Glide.with(this).asBitmap().circleCrop().load(imageBitmap).into(edit_profile_image)
            }
            edit_profile_name.setText(it.name)
            edit_profile_description.setText(it.description)
        })

        edit_profile_save.setOnClickListener {
            val profileToSave = Profile(edit_profile_name.text.toString(), edit_profile_description.text.toString(), imagePath)
            profileViewModel.updateProfile(profileToSave)
            val toast = Toast.makeText(this, R.string.user_saved, Toast.LENGTH_SHORT)
            toast.show()
        }

        edit_profile_image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, Constants.PERMISSION_CODE)
                } else {
                    openCamera()
                }
            } else {
                openCamera()
            }
        }

    }

    // Fonction pour le lancement de la camera
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, Constants.REQUEST_CODE_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == Constants.REQUEST_CODE_CAMERA) {
            val imageTaken = data?.extras?.get("data") as Bitmap
            imagePath = ImageManager().encodeImage(imageTaken)
            Glide.with(this).asBitmap().circleCrop().load(imageTaken).into(edit_profile_image)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // Demande des permissions pour l'ouverture de la camera et de l'écriture dans les fichiers
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show()
        }
    }


}