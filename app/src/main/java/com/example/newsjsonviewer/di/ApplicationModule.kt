package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.data.repository.NewsProvider
import com.example.newsjsonviewer.framework.network.NetworkManager
import com.example.newsjsonviewer.framework.network.NewsProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ApplicationModule {

    @Provides
    @Singleton
    fun provideNewsProvider(networkManager: NetworkManager): NewsProvider = NewsProviderImpl(networkManager)

}