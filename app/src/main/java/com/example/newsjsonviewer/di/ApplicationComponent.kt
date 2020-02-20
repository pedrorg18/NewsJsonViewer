package com.example.newsjsonviewer.di

import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent : IAppComponent