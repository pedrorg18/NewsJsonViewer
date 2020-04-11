package com.example.newsjsonviewer.testutils

import org.mockito.Mockito

fun <T> anyObject(): T {
    @Suppress("DEPRECATION")
    return Mockito.anyObject<T>()
}