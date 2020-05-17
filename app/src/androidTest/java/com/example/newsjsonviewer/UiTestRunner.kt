package com.example.newsjsonviewer

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

@Suppress("unused") // used in the manifest
class UiTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application =
        super.newApplication(cl, MockUiTestApplication::class.java.name, context)
}