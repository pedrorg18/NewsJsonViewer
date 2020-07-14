package com.example.newsjsonviewer.data.database

import com.example.newsjsonviewer.data.database.dao.CachedNewsDao
import com.example.newsjsonviewer.data.repository.CACHED_NEWS_EXPIRATION_TIME
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.test.factory.randomLong
import com.example.newsjsonviewer.test.mock.createCachedNews
import com.example.newsjsonviewer.test.mock.createRandomDbNews
import com.example.newsjsonviewer.test.mock.generateMockDomainNewsListUs
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsDbDataSourceImplTest {

    private val mockCachedNewsDao = mock<CachedNewsDao>()
    private val mockDb = mock<NewsDatabase>()
    private val dbDataSource = NewsDbDataSourceImpl(mockDb)

    @Before
    fun setup() {
        whenever(mockDb.cachedNewsDao())
            .thenReturn(mockCachedNewsDao)
    }

    @Test
    fun getCachedNewsNoCache_completes() {
        val country = Country.Usa
        stubDatabaseFindNews(country.name, Maybe.empty<CachedNewsDao.CachedNewsAndDbNews>())
        dbDataSource.getCachedNews(country, randomLong()).test().assertComplete()
    }

    @Test
    fun getCachedNewsNoCache_returnsEmptyList() {
        val country = Country.Usa
        stubDatabaseFindNews(country.name, Maybe.empty<CachedNewsDao.CachedNewsAndDbNews>())
        dbDataSource.getCachedNews(country, randomLong()).test().assertValue(emptyList())
    }

    @Test
    fun getCachedNewsExpired_returnsEmptyList() {
        val country = Country.Usa
        stubDatabaseFindNews(country.name, Maybe.just(
            CachedNewsDao.CachedNewsAndDbNews().apply {
                // set expiration time current time - expiration time
                cachedNews = createCachedNews(
                    country,
                    System.currentTimeMillis() - CACHED_NEWS_EXPIRATION_TIME
                )
                dbNewsList = listOf(
                    createRandomDbNews(country.name),
                    createRandomDbNews(country.name),
                    createRandomDbNews(country.name)
                )
            }
        ))
        dbDataSource.getCachedNews(country, CACHED_NEWS_EXPIRATION_TIME).test()
            .assertValue(emptyList())
    }

    /**
     * set expiration time to 1 second ago, so it's not yet expired
     */
    @Test
    fun getCachedNewsNonExpired_returnsData() {
        val country = Country.Usa
        stubDatabaseFindNews(country.name, Maybe.just(
            CachedNewsDao.CachedNewsAndDbNews().apply {
                // set expiration time current time - 1 second
                cachedNews = createCachedNews(country, System.currentTimeMillis() - 1000)
                dbNewsList = listOf(
                    createRandomDbNews(country.name),
                    createRandomDbNews(country.name),
                    createRandomDbNews(country.name)
                )
            }
        ))
        assertEquals(
            3,
            dbDataSource.getCachedNews(country, CACHED_NEWS_EXPIRATION_TIME).test()
                .values()[0].size
        )
    }

    @Test
    fun saveNewsToCache_completes() {
        val country = Country.Usa
        val news = generateMockDomainNewsListUs()
        // insert returns success
        stubDatabaseInsertNews(true)
        dbDataSource.saveNewsToCache(country, news).test().assertComplete()
    }

    @Test
    fun saveNewsToCache_fails() {
        val country = Country.Usa
        val news = generateMockDomainNewsListUs()
        // insert returns NO success
        stubDatabaseInsertNews(false)
        dbDataSource.saveNewsToCache(country, news).test().assertError(RuntimeException::class.java)
    }

    private fun stubDatabaseFindNews(country: String, result: Maybe<CachedNewsDao.CachedNewsAndDbNews>) {
        whenever(mockCachedNewsDao.findCachedNewsAndDbNewsByCountry(country))
            .thenReturn(result)
    }

    private fun stubDatabaseInsertNews(result: Boolean) {
        whenever(mockCachedNewsDao.insertCachedNewsAndNewsList(any(), any()))
            .thenReturn(result)
    }

}