package com.example.newsjsonviewer.mock.module

import androidx.room.Room
import com.example.newsjsonviewer.data.database.NewsDatabase
import com.example.newsjsonviewer.data.database.NewsDbDataSourceImpl
import com.example.newsjsonviewer.data.datasource.NewsDbDataSource
import com.example.newsjsonviewer.mock.NewsMockApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockDatabaseModule {

    @Provides
    fun providesDatabase(app: NewsMockApplication) = Room.inMemoryDatabaseBuilder(
        app,
        NewsDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideNewsDbDataSource(newsDatabase: NewsDatabase): NewsDbDataSource =
        NewsDbDataSourceImpl(newsDatabase)

}