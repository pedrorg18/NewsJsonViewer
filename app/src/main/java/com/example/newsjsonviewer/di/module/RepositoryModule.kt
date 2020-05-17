package com.example.newsjsonviewer.di.module

import com.example.newsjsonviewer.data.datasource.NewsDataSource
import com.example.newsjsonviewer.data.repository.NewsRepositoryImpl
import com.example.newsjsonviewer.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesNewsRepository(dataSource: NewsDataSource): NewsRepository =
        NewsRepositoryImpl(dataSource)
}