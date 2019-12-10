package com.example.newsjsonviewer.framework.app

import android.app.Application
import com.example.newsjsonviewer.di.ApplicationComponent
import com.example.newsjsonviewer.di.ApplicationModule
import com.example.newsjsonviewer.di.DaggerApplicationComponent

open class NewsApplication : Application() {

    private var applicationComponent: ApplicationComponent? = null

    open fun getComponent(): ApplicationComponent {
        if(applicationComponent == null)
            applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule())
                .build()

        return applicationComponent!!
    }
}