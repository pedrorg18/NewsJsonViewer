package com.example.newsjsonviewer.ui.image

import android.widget.ImageView


fun loadImage(imageView: ImageView) {
    GlideApp.with(imageView.context)
        .load("http://via.placeholder.com/300.png")
        .into(imageView)

}