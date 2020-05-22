package com.example.newsjsonviewer.globals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.newsjsonviewer.features.newsdetail.NewsDetailActivity
import com.example.newsjsonviewer.features.newslist.NewsListActivity


/**
 * Base activity for all activities to extend. It manages dagger injection
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * Provides an IdlingResource to use in Espresso tests
     */
    abstract fun getIdlingResource(): CountingIdlingResource?

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

}