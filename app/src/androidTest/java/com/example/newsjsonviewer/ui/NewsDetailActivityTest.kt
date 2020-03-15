package com.example.newsjsonviewer.ui

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class NewsDetailActivityTest {

    lateinit var scenario: ActivityScenario<NewsDetailActivity>

    @Before
    @Throws(Exception::class)
    fun setup() {
        scenario = ActivityScenario.launch(
            Intent(ApplicationProvider.getApplicationContext(), NewsDetailActivity::class.java)
                .apply {
                    putExtra(NEWS_TO_SHOW_DETAIL_EXTRA,
                        generateFirstMockDomainNewsFromUSList())
                })
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun test_detailActivity() {
        // check title is correctly displayed
        onView(
            allOf(
                withId(R.id.tvDetailTitle),
                withText("Roger Federer vs. Novak Djokovic live score: Wimbledon 2019 final updates as the championship heads to a fifth set - CBS Sports")
            )
        )
                .check(matches(isDisplayed()))

        // check subtitle is correctly displayed
        onView(
            allOf(
                withId(R.id.tvDetailSubtitle),
                withText("The matchup in 2019 finals at the All England Club is all we could have hoped for")
            )
        )
            .check(matches(isDisplayed()))

        // check content is correctly displayed
        onView(
            allOf(
                withId(R.id.tvDetailcontent),
                withText(containsString("Sure, if Rafael Nadal was on the marquee for the match, it might have the same sizzle"))
            )
        )
            .check(matches(isDisplayed()))

        // check author is correctly displayed
        onView(
            allOf(
                withId(R.id.tvDetailAuthorAndDate),
                withText(containsString("Andrew Julian · "))
            )
        )
            .check(matches(isDisplayed()))

    }


    private fun generateFirstMockDomainNewsFromUSList() =
        News(
            "Roger Federer vs. Novak Djokovic live score: Wimbledon 2019 final updates as the championship heads to a fifth set - CBS Sports",
            "The matchup in 2019 finals at the All England Club is all we could have hoped for",
            "https://sportshub.cbsistatic.com/i/r/2019/07/13/cb4a9511-605b-4571-8d03-060d65e1d31d/thumbnail/1200x675/6b18dbf46bc11fd6f41c7e88abc7846c/usatsi-13024725.jpg",
            "Andrew Julian",
            "https://www.cbssports.com/tennis/news/roger-federer-vs-novak-djokovic-live-score-wimbledon-2019-final-updates-as-the-championship-heads-to-a-fifth-set/",
            Date(),
            "Could it possibly be any bigger? \r\nSure, if Rafael Nadal was on the marquee for the match, it might have the same sizzle, but the tennis world will be treated to No. 1 seed Novak Djokovic (third all-time in Grand Slam wins with 15) taking on No. 2 seed Roger … [+971 chars]"
        )

}