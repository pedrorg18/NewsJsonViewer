package com.example.newsjsonviewer.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsjsonviewer.data.database.NewsDatabase
import com.example.newsjsonviewer.data.database.model.CachedNews
import com.example.newsjsonviewer.test.mock.generateRandomDbMockNews
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CachedNewsDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var dao: CachedNewsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            NewsDatabase::class.java
        )
            .build()
        dao = database.cachedNewsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getNews_returnsData() {
        val country = "usa"

        // Create cached news entity (without news yet), insert them
        val cachedNews = CachedNews(
            country,
            System.currentTimeMillis()
        )
        dao.insert(cachedNews)

        // create news list and associate to the cached news entity (by country), insert them
        val news = listOf(
            generateRandomDbMockNews(country),
            generateRandomDbMockNews(country)
        )
        dao.insertNewsList(news)

        // fetch cachedNews entity and associated news list
        val fetchedData =
            dao.findCachedNewsAndDbNewsByCountry(country).test().values().first()

        assertEquals(cachedNews, fetchedData.cachedNews)
        // check news list are the same as inserted.
        // NOTE: before comparison we must set the ID of the original news with the news ID from db,
        // as the latter is autogenerated by db and then the comparison would fail
        news.forEachIndexed { index, value ->
            fetchedData.dbNewsList[index].apply { id = value.id }
        }
        assertEquals(news, fetchedData.dbNewsList)
    }

    @Test
    fun insertCachedNewsAndNewsList_insertsData() {
        val country = "usa"

        val cachedNews = CachedNews(
            country,
            System.currentTimeMillis()
        )
        val news = listOf(
            generateRandomDbMockNews(country),
            generateRandomDbMockNews(country)
        )
        dao.insertCachedNewsAndNewsList(cachedNews, news)

        // fetch cachedNews entity and associated news list
        val fetchedData =
            dao.findCachedNewsAndDbNewsByCountry(country).test().values().first()

        assertEquals(cachedNews, fetchedData.cachedNews)
        // check news list are the same as inserted.
        // NOTE: before comparison we must set the ID of the original news with the news ID from db,
        // as the latter is autogenerated by db and then the comparison would fail
        news.forEachIndexed { index, value ->
            fetchedData.dbNewsList[index].apply { id = value.id }
        }
        assertEquals(news, fetchedData.dbNewsList)
    }


    @Test
    fun insertCachedNewsAndNewsListErrorInsertingNews_performsRollback() {
        val country = "usa"

        val cachedNews = CachedNews(
            country,
            System.currentTimeMillis()
        )
        // these news are gonna fail: heir country is not the same as the CachedNews -> Foreign Key exception
        val news = listOf(
            generateRandomDbMockNews("France"),
            generateRandomDbMockNews("France")
        )
        dao.insertCachedNewsAndNewsList(cachedNews, news)


        val fetchedData =
            dao.findCachedNewsAndDbNewsByCountry(country).test().values()

        // Check the cached news were rolled back after the news insertion failed
        assertTrue(fetchedData.isEmpty())
    }

}