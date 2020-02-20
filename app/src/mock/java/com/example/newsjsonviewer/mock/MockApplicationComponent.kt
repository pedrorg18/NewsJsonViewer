package com.example.newsjsonviewer.mock

import com.example.newsjsonviewer.di.IAppComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockApplicationModule::class])
interface MockApplicationComponent : IAppComponent