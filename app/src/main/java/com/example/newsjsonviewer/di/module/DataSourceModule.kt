package com.example.newsjsonviewer.di.module

import com.example.newsjsonviewer.data.datasource.NewsDataSource
import com.example.newsjsonviewer.data.datasource.NewsDataSourceImpl
import com.example.newsjsonviewer.data.network.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideNewsDataSource(networkManager: NetworkManager): NewsDataSource =
        NewsDataSourceImpl(
            networkManager
        )
}