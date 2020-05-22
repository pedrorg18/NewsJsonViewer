@file:Suppress("unused")

package com.example.newsjsonviewer.uitest

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Provides [CountingIdlingResource] for Espresso testing. This class  has a double in production
 * code that returns a null value
 */
object IdlingResourceProvider {
    fun provideIdlingResource(className: String): CountingIdlingResource? =
            CountingIdlingResource(className)
}