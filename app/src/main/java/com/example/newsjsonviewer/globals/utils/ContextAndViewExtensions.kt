package com.example.newsjsonviewer.globals.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.NonNull
import android.view.View
import android.widget.Toast


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisibilize() {
    visibility = View.INVISIBLE
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
