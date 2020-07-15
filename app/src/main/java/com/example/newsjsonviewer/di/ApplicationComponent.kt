package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.di.module.*
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface ApplicationComponent : IAppComponent