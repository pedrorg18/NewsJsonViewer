package com.example.newsjsonviewer.di.module

import com.example.newsjsonviewer.data.datasource.NewsRemoteDataSource
import com.example.newsjsonviewer.data.network.NewsRemoteDataSourceImpl
import com.example.newsjsonviewer.data.network.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(networkManager: NetworkManager): NewsRemoteDataSource =
        NewsRemoteDataSourceImpl(
            networkManager
        )
}