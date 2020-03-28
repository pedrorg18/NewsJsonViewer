package com.example.newsjsonviewer.mock

import com.example.newsjsonviewer.data.repository.NewsProvider
import com.example.newsjsonviewer.framework.network.NetworkManager
import com.example.newsjsonviewer.framework.network.NewsProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockApplicationModule(private val mockApp: NewsMockApplication) {

    private val REQUEST_TIMEOUT = 60

    @Provides
    @Singleton
    fun provideNewsProvider(networkManager: NetworkManager): NewsProvider = NewsProviderImpl(networkManager)
}