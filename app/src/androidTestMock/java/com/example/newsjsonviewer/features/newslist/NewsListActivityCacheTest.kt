package com.example.newsjsonviewer.features.newslist

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.newsjsonviewer.MockUiTestApplication
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.data.database.NewsDbDataSourceImpl
import com.example.newsjsonviewer.data.repository.CACHED_NEWS_EXPIRATION_TIME
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.utils.recyclerViewSizeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class NewsListActivityCacheTest {

    lateinit var scenario: ActivityScenario<NewsListActivity>
    private lateinit var idlingResource: IdlingResource
    @Inject
    lateinit var dbDataSourceImpl: NewsDbDataSourceImpl

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
        // clean in-memory database to avoid affecting other tests
        dbDataSourceImpl.saveNewsToCache(Country.Usa, emptyList()).test().assertComplete()
        scenario.close()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun newsListActivityTest_cachesProperly() {

        // check recyclerView is displayed and has 17 elements (obtained from Remote)
        Espresso.onView(ViewMatchers.withId(R.id.rvNewsList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(recyclerViewSizeMatcher(17)))

        // change the database cache by removing the last element
        dbDataSourceImpl.saveNewsToCache(
            Country.Usa,
            dbDataSourceImpl.getCachedNews(Country.Usa, CACHED_NEWS_EXPIRATION_TIME)
                .test().values()[0].dropLast(1)
        ).test().assertComplete()

        // recreate the activity
        scenario.recreate()

        // check that now we have 16 elements -> therefore we got them from cache instead of remote
        Espresso.onView(ViewMatchers.withId(R.id.rvNewsList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(recyclerViewSizeMatcher(16)))
    }

}