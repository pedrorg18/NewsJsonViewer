package com.example.newsjsonviewer

import com.example.newsjsonviewer.di.DaggerMockUiTestApplicationComponent
import com.example.newsjsonviewer.di.MockUiTestApplicationComponent
import com.example.newsjsonviewer.mock.NewsMockApplication

/**
 * Application object for UI tests. It extends [NewsMockApplication], thereby getting mocked local
 * server un and running, Retrofit calls pointing to it
 */
class MockUiTestApplication : NewsMockApplication() {

    private var mockAppComponent: MockUiTestApplicationComponent? = null

    fun getTestComponent(): MockUiTestApplicationComponent {
        if(mockAppComponent == null)
            mockAppComponent = DaggerMockUiTestApplicationComponent.builder()
                .build()

        return mockAppComponent!!
    }
}