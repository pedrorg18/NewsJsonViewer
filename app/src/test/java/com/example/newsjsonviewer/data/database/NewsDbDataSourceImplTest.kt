package com.example.newsjsonviewer.data.database

import com.example.newsjsonviewer.data.database.dao.CachedNewsDao
import com.example.newsjsonviewer.data.repository.CACHED_NEWS_EXPIRATION_TIME
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.test.factory.randomLong
import com.example.newsjsonviewer.test.mock.createCachedNews
import com.example.newsjsonviewer.test.mock.createRandomDbNews
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsDbDataSourceImplTest {

    private val mockCachedNewsDao = mock<CachedNewsDao>()
    private val mockDb = mock<NewsDatabase>()
    private val dbDataSource = NewsDbDataSourceImpl(mockDb)

    @Test
    fun getCachedNewsNoCache_completes() {
        val country = Country.Usa
        stubDatabase(country.name, Maybe.empty<CachedNewsDao.CachedNewsAndDbNews>())
        dbDataSource.getCachedNews(country, randomLong()).test().assertComplete()
    }

    @Test
    fun getCachedNewsNoCache_returnsEmptyList() {
        val country = Country.Usa
        stubDatabase(country.name, Maybe.empty<CachedNewsDao.CachedNewsAndDbNews>())
        dbDataSource.getCachedNews(country, randomLong()).test().assertValue(emptyList())
    }

    @Test
    fun getCachedNewsExpired_returnsEmptyList() {
        val country = Country.Usa
        stubDatabase(country.name, Maybe.just(
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
        stubDatabase(country.name, Maybe.just(
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
    fun saveNewsToCache() {
    }

    private fun stubDatabase(country: String, result: Maybe<CachedNewsDao.CachedNewsAndDbNews>) {
        whenever(mockDb.cachedNewsDao())
            .thenReturn(mockCachedNewsDao)
        whenever(mockCachedNewsDao.findCachedNewsAndDbNewsByCountry(country))
            .thenReturn(result)
    }


}