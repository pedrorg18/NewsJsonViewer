package com.example.newsjsonviewer.mock

import com.example.newsjsonviewer.di.IAppComponent
import com.example.newsjsonviewer.di.module.RepositoryModule
import com.example.newsjsonviewer.mock.module.MockApplicationModule
import com.example.newsjsonviewer.mock.module.MockDatabaseModule
import com.example.newsjsonviewer.mock.module.MockNetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MockApplicationModule::class,
        MockNetworkModule::class,
        MockDatabaseModule::class,
        RepositoryModule::class
    ]
)
interface MockApplicationComponent : IAppComponent