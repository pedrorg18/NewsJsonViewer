package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.data.datasource.NewsDataSource
import com.example.newsjsonviewer.data.network.NetworkManager
import com.example.newsjsonviewer.data.datasource.NewsDataSourceImpl
import com.example.newsjsonviewer.data.repository.NewsRepositoryImpl
import com.example.newsjsonviewer.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ApplicationModule {

    @Provides
    @Singleton
    fun provideNewsDataSource(networkManager: NetworkManager): NewsDataSource =
        NewsDataSourceImpl(
            networkManager
        )

    @Provides
    @Singleton
    fun providesNewsRepository(dataSource: NewsDataSource): NewsRepository =
        NewsRepositoryImpl(dataSource)

}