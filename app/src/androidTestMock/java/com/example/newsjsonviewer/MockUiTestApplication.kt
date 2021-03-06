package com.example.newsjsonviewer

import com.example.newsjsonviewer.di.DaggerMockUiTestApplicationComponent
import com.example.newsjsonviewer.di.MockUiTestApplicationComponent
import com.example.newsjsonviewer.mock.NewsMockApplication
import com.example.newsjsonviewer.mock.module.MockApplicationModule

/**
 * Application object for UI tests. It extends [NewsMockApplication], thereby getting mocked local
 * server un and running, Retrofit calls pointing to it
 */
class MockUiTestApplication : NewsMockApplication() {

    private var mockAppComponent: MockUiTestApplicationComponent? = null

    override fun getComponent(): MockUiTestApplicationComponent {
        if(mockAppComponent == null)
            mockAppComponent = DaggerMockUiTestApplicationComponent.builder()
                .mockApplicationModule(MockApplicationModule(this))
                .build()

        return mockAppComponent!!
    }

}