package com.example.newsjsonviewer.globals

import android.annotation.SuppressLint
import android.app.Application
import com.example.newsjsonviewer.di.DaggerApplicationComponent
import com.example.newsjsonviewer.di.IAppComponent
import com.example.newsjsonviewer.di.module.ApplicationModule

// In fact it's registered in the manifest. Warning caused because there is a mock subclass
@SuppressLint("Registered")
open class NewsApplication : Application() {

    protected var applicationComponent: IAppComponent? = null

    open fun getComponent(): IAppComponent {
        if(applicationComponent == null)
            applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(
                    ApplicationModule(this)
                )
                .build()

        return applicationComponent!!
    }
}