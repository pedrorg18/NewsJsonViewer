package com.example.newsjsonviewer.ui.image

import android.widget.ImageView


fun loadImage(imageView: ImageView, imageUrl: String) {
    GlideApp.with(imageView.context)
        .load(imageUrl)
        .into(imageView)

}