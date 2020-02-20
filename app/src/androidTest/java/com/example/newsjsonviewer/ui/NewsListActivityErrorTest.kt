package com.example.newsjsonviewer.ui


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.utils.recyclerViewSizeMatcher
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class NewsListActivityErrorTest {

    lateinit var scenario: ActivityScenario<NewsListActivity>
//    private lateinit var webServer: MockWebServer


    @Before
    @Throws(Exception::class)
    fun setup() {
//        launchMockWebServer()

        scenario = ActivityScenario.launch(NewsListActivity::class.java)
        scenario.onActivity {
            val idlingResource = it.getIdlingResource()!!
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
//        webServer.shutdown()
        scenario.close()
    }


    @Test
    fun newsList_serverCallFails() {

//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        webServer.dispatcher = MockServerDispatcher(appContext).ErrorDispatcher()

        // neither RecyclerView nor the Shimmer should be visible, only empty screen
        onView(
            withId(R.id.rvNewsList)
        ).check(matches(recyclerViewSizeMatcher(0)))
        onView(
            withId(R.id.shimmerViewContainer)
        ).check(matches(not(isDisplayed())))

    }


//    private fun launchMockWebServer() {
//        webServer = MockWebServer()
//        webServer.start(InetAddress.getByName("localhost"), 8080)
//
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        webServer.dispatcher = MockServerDispatcher(appContext).ErrorDispatcher()
//    }

}
