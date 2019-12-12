package com.example.newsjsonviewer.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.server.MockServerDispatcher
import com.example.newsjsonviewer.utils.recyclerViewAtPositionOnView
import com.example.newsjsonviewer.utils.recyclerViewSizeMatcher
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.InetAddress


@LargeTest
@RunWith(AndroidJUnit4::class)
class NewsListActivityTest {

    lateinit var scenario: ActivityScenario<NewsListActivity>
    private lateinit var webServer: MockWebServer


    @Before
    @Throws(Exception::class)
    fun setup() {
        launchMockWebServer()

        scenario = ActivityScenario.launch(NewsListActivity::class.java)
        scenario.onActivity {
            val idlingResource = it.getIdlingResource()!!
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        webServer.shutdown()
        scenario.close()
    }

    @Test
    fun newsListActivityTest() {

        // Click on the first element of the List
        val constraintLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rvNewsList),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        pressBack()

        // check RV has 17 elements
        onView(
            withId(R.id.rvNewsList)
        ).check(matches(recyclerViewSizeMatcher(17)))

        // check first element in the list has certain title
        onView(withId(R.id.rvNewsList)).check(
            matches(recyclerViewAtPositionOnView(0, withText("Roger Federer vs. Novak Djokovic live score: Wimbledon 2019 final updates as the championship heads to a fifth set - CBS Sports"), R.id.tvListItemTitle))
        )

        // check first element in the list is displayed
        val textView2 = onView(
            allOf(
                withId(R.id.tvListItemTitle),
                withText(containsString("Wimbledon 2019 final updates as the championship heads to a fifth set")),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rvNewsList),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))

        // check first element in the list has certain content
        onView(withId(R.id.rvNewsList)).check(
            matches(recyclerViewAtPositionOnView(0, withText("The matchup in 2019 finals at the All England Club is all we could have hoped for"), R.id.tvListItemContent))
        )

    }


    private fun launchMockWebServer() {
        webServer = MockWebServer()
        webServer.start(InetAddress.getByName("localhost"), 8080)

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        webServer.dispatcher = MockServerDispatcher(appContext).RequestDispatcher()
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
