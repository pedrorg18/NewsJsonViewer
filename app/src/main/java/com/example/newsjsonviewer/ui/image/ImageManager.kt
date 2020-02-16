package com.example.newsjsonviewer.ui.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener


fun loadImage(
    imageView: ImageView,
    imageUrl: String,
    imageLoadListener: RequestListener<Drawable>? = null
) {
    GlideApp.with(imageView.context)
        .load(imageUrl)
        .apply {
            imageLoadListener?.let {
                listener(imageLoadListener)
            }
        }
        .listener(imageLoadListener)
        .into(imageView)

}