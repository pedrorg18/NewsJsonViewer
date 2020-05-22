@file:Suppress("unused")

package com.example.newsjsonviewer.uitest

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Provides [CountingIdlingResource] for Espresso testing. This class  has a double in production
 * code that returns a null value
 *
 * The value for UI test is wrapped in a try / catch: this is because the unit tests will pick
 * this file instead of the production one, then [CountingIdlingResource] initialization would
 * fail as it uses the Android Framework.
 * In this case we return null, as IdlingResources are not used in unit tests
 */
object IdlingResourceProvider {
    fun provideIdlingResource(className: String): CountingIdlingResource? =
        try {
            CountingIdlingResource(className)
        } catch (e: Exception) {
            null
        }
}