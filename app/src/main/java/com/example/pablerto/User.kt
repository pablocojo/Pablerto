package com.example.pablerto

import android.graphics.Bitmap
import java.io.Serializable

class User(var name: String, var password: String, var images: MutableList<Bitmap> = mutableListOf()) : Serializable {
    fun addImage(image: Bitmap) {
        images.add(image)
    }
}
