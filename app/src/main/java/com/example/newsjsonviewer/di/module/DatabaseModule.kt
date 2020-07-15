package com.example.newsjsonviewer.di.module

import android.app.Application
import com.example.newsjsonviewer.data.database.NewsDatabase
import com.example.newsjsonviewer.data.database.NewsDbDataSourceImpl
import com.example.newsjsonviewer.data.datasource.NewsDbDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun providesDatabase(app: Application) = NewsDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideNewsDbDataSource(newsDatabase: NewsDatabase): NewsDbDataSource =
        NewsDbDataSourceImpl(newsDatabase)

}