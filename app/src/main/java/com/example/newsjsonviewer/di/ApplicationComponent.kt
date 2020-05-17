package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.di.module.ApplicationModule
import com.example.newsjsonviewer.di.module.DataSourceModule
import com.example.newsjsonviewer.di.module.NetworkModule
import com.example.newsjsonviewer.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface ApplicationComponent : IAppComponent