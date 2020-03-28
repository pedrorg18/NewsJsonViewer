package com.example.newsjsonviewer.globals.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener


fun loadImage(
    imageView: ImageView,
    imageUrl: String,
    imageLoadListener: RequestListener<Drawable>? = null
) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .apply {
            imageLoadListener?.let {
                listener(imageLoadListener)
            }
        }
        .into(imageView)

}