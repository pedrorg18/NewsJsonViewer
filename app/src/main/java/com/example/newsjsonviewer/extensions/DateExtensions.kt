package com.example.newsjsonviewer.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDdMmYyyyHhMm(): String {
    val pattern = "dd-MM-yyyy HH:mm"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
    return simpleDateFormat.format(this)
}