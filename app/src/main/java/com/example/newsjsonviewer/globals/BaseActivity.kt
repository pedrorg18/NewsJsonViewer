package com.example.newsjsonviewer.globals

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.newsjsonviewer.framework.app.NewsApplication
import com.example.newsjsonviewer.ui.NewsDetailActivity
import com.example.newsjsonviewer.ui.NewsListActivity


/**
 * Base activity for all activities to extend. It manages dagger injection
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity() // before super.onCreate() to avoid issues with fragment restoration
        super.onCreate(savedInstanceState)
    }

    private fun injectActivity() {
        when(this) {
            is NewsListActivity -> (application as NewsApplication).getComponent()
                .inject(this)
            is NewsDetailActivity -> (application as NewsApplication).getComponent()
                .inject(this)

            else -> throw IllegalArgumentException(
                "You must inject the activity into Dagger: ${this::javaClass.name}")
        }
    }

    protected var idlingResource: CountingIdlingResource? = null

    /**
     * Only called from test, creates and returns a new [CountingIdlingResource].
     */
    @VisibleForTesting
    fun idlingResource(): CountingIdlingResource? {
        if (idlingResource == null) {
            idlingResource = CountingIdlingResource(this.localClassName)
        }
        return idlingResource
    }

}