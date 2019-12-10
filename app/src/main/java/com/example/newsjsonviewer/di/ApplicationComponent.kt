package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.data.repository.NewsRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun newsRepository(): NewsRepository
}