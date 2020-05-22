package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.di.module.DataSourceModule
import com.example.newsjsonviewer.di.module.RepositoryModule
import com.example.newsjsonviewer.features.newslist.NewsListActivityTest
import com.example.newsjsonviewer.mock.module.MockNetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component for UI tests. It allows us to inject test classes into the graph
 */
@Singleton
@Component(
    modules = [
        MockNetworkModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
interface MockUiTestApplicationComponent : IAppComponent {
    fun inject(newsListActivityTest: NewsListActivityTest)
}