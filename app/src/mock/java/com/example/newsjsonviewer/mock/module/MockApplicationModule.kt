package com.example.newsjsonviewer.mock.module

import com.example.newsjsonviewer.mock.NewsMockApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockApplicationModule(private val mockApp: NewsMockApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext() = mockApp
}