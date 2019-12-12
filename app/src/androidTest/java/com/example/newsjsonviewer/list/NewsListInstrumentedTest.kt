package com.example.newsjsonviewer.list

//import org.hamcrest.Matchers.`is`

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.NewsListActivity
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NewsListInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(NewsListActivity::class.java)

    @Before fun registerIdlingResource() {
        val idlingResource = activityRule.activity.getIdlingResource()!!
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @Test fun listScreenShowsOk() {
        println("INST_TEST - INIT")

//        onView(withId(R.id.rvNewsList)).check(matches(isDisplayed()))
////        onView(withText(containsString("Cowboys loss"))).check(matches(isDisplayed()))
//
//        onData(
//            allOf(
//                `is`(instanceOf(News::class.java)),
//                hasEntry(equalTo("title"), `is`("Odell Beckham-like urinating dog celebration costs Ole Miss the Egg Bowl - New York Post "))
//            )
//        )
//            .perform(click())

        println("INST_TEST - END")
    }

    @Test fun checkNewsList() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        generateMockNewsListUS(appContext)
    }
}