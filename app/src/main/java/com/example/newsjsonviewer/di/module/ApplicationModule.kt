package com.example.newsjsonviewer.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext() = app

}