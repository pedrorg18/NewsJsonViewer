package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.framework.app.NewsApplication


class TestApplication : NewsApplication() {

    override fun getComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .applicationModule(TestApplicationModule(this))
            .build()
    }

}