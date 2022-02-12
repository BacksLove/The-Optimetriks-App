package com.example.theoptimetriksapp.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.theoptimetriksapp.R
import com.example.theoptimetriksapp.utilities.ImageManager

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val profileImage = root.findViewById<ImageView>(R.id.profile_imageview)
        val profileName = root.findViewById<TextView>(R.id.profile_name)
        val profileDescription = root.findViewById<TextView>(R.id.profile_description)
        val profileEditButton = root.findViewById<Button>(R.id.profile_edit_button)

        profileViewModel.getProfile()
        profileViewModel.profile.observe(viewLifecycleOwner, Observer {
            if (it.image.isNotEmpty()) {
                val imageBitmap = ImageManager().decodeImage(it.image)
                Glide.with(this).asBitmap().circleCrop().load(imageBitmap).into(profileImage)
            }
            profileName.text = it.name
            profileDescription.text = it.description
        })

        profileEditButton.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        return root
    }

}