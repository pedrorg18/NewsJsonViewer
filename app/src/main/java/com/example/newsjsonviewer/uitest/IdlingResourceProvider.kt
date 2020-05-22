package com.example.newsjsonviewer.uitest

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Provides [CountingIdlingResource] for UI Espresso testing. In this case oit will be null.
 * There is a double of this class in the androidTest folder that provides an actual
 * [CountingIdlingResource] for the UI test environment
 */
object IdlingResourceProvider {
    @Suppress("UNUSED_PARAMETER")
    fun provideIdlingResource(className: String): CountingIdlingResource? = null
}