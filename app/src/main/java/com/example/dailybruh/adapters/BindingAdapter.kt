package com.example.dailybruh.adapters

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.dailybruh.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView,imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            placeholder(R.drawable.animation_load)
            error(R.drawable.icon_broken_image)
        }
    }

}