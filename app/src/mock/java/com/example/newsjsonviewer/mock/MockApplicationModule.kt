package com.example.newsjsonviewer.mock

import com.example.newsjsonviewer.data.datasource.NewsDataSource
import com.example.newsjsonviewer.data.network.NetworkManager
import com.example.newsjsonviewer.data.datasource.NewsDataSourceImpl
import com.example.newsjsonviewer.data.repository.NewsRepositoryImpl
import com.example.newsjsonviewer.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockApplicationModule(private val mockApp: NewsMockApplication) {

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