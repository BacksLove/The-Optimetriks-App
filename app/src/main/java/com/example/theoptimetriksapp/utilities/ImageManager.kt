package com.example.theoptimetriksapp.utilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageManager {

    fun encodeImage(image: Bitmap) : String {
        val baos = ByteArrayOutputStream()

        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        return encodedImage
    }

    fun decodeImage(imagePath: String) : Bitmap {
        val imageBytes = Base64.decode(imagePath, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

}