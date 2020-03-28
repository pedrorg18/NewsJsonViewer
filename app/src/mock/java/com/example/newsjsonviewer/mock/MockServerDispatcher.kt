package com.example.newsjsonviewer.mock

import android.content.Context
import com.example.newsjsonviewer.data.network.GET_LATEST_NEWS_PATH
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

internal class MockServerDispatcher(private val appContext: Context) {


    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        var fail = false
        override fun dispatch(request: RecordedRequest): MockResponse {

            if(fail) return MockResponse().setResponseCode(404)

            if (request.path.contains(GET_LATEST_NEWS_PATH)) {
                val jsonBody: String = AssetReaderUtil.asset(appContext, "mock_us_news_list.json")
                return MockResponse().setResponseCode(200).setBody(jsonBody)
            }
//            } else if (request.path == "api/codes") {
//                return MockResponse().setResponseCode(200).setBody("{codes:FakeCode}")
//            } else if (request.path == "api/number") return MockResponse().setResponseCode(
//                200
//            ).setBody("number:FakeNumber")
            return MockResponse().setResponseCode(404)
        }
    }

    /**
     * Return error response from mock server
     */
    @Suppress("unused")
    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest?): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }
}