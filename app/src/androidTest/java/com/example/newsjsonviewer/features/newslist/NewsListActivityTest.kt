package com.example.newsjsonviewer.features.newslist


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.newsjsonviewer.MockUiTestApplication
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.utils.childAtPosition
import com.example.newsjsonviewer.utils.recyclerViewAtPositionOnView
import com.example.newsjsonviewer.utils.recyclerViewSizeMatcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class NewsListActivityTest {

    lateinit var scenario: ActivityScenario<NewsListActivity>
    private lateinit var idlingResource: IdlingResource

    @Before
    @Throws(Exception::class)
    fun setup() {
        scenario = ActivityScenario.launch(NewsListActivity::class.java)
        scenario.onActivity {
            (it.application as MockUiTestApplication).getComponent().inject(this)

            idlingResource = it.getIdlingResource()!!
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        scenario.close()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun newsListActivityTest() {

        // check shimmer is hidden
        onView(withId(R.id.shimmerViewContainer))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        // check recyclerView is displayed and has 17 elements
        onView(withId(R.id.rvNewsList))
            .check(matches(isDisplayed()))
            .check(matches(recyclerViewSizeMatcher(17)))

        // get first element from RV
        val firstListElementInteraction = onView(
            allOf(
                childAtPosition(withId(R.id.rvNewsList), 0),
                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout"))
            )
        )
        // Click on the first element of the List
        firstListElementInteraction.perform(click())

        // check detail activity is displayed
        onView(withId(R.id.root_view_detail))
            .check(matches(isDisplayed()))

        pressBack()

        // re-check RV has 17 elements
        onView(
            withId(R.id.rvNewsList)
        ).check(matches(recyclerViewSizeMatcher(17)))

        // check first element in the list has certain title
        recyclerViewAtPositionOnView(
            0,
            withText("Roger Federer vs. Novak Djokovic live score: Wimbledon 2019 final updates as the championship heads to a fifth set - CBS Sports"),
            R.id.tvListItemTitle
        )

        // check first element in the list has certain description
        recyclerViewAtPositionOnView(
            0,
            withText("Sure, if Rafael Nadal was on the marquee for the match, it might have the same sizzle"),
            R.id.tvListItemContent
        )

        // check second element in the list has certain title
        recyclerViewAtPositionOnView(
            1,
            withText("Trump tweets racist attacks at progressive Democratic congresswomen - CNN"),
            R.id.tvListItemTitle
        )
    }

}
