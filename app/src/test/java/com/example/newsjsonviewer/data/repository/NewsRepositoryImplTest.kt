package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.data.datasource.NewsDbDataSource
import com.example.newsjsonviewer.data.datasource.NewsRemoteDataSource
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.test.factory.randomString
import com.example.newsjsonviewer.test.mock.generateMockDomainNewsListUs
import com.example.newsjsonviewer.test.mock.generateRandomMockNews
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Test

class NewsRepositoryImplTest {

    private val mockRemoteDs = mock<NewsRemoteDataSource>()
    private val mockDataBaseDs = mock<NewsDbDataSource>()
    private val repo = NewsRepositoryImpl(mockRemoteDs, mockDataBaseDs)

    @Test
    fun getLatestNewsNoCache_completes() {
        stubDb(Country.Usa, Single.just(generateMockDomainNewsListUs()))

        repo.getLatestNews(Country.Usa).test().assertComplete()
    }

    @Test
    fun getLatestNewsNoCache_returnsRemoteData() {
        val result = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.just(emptyList()))
        stubRemote(Country.Usa, Single.just(result))

        repo.getLatestNews(Country.Usa).test().assertResult(result)
    }

    @Test
    fun getLatestNewsNoCache_savesRemoteDataAsCache() {
        val result = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.just(emptyList()))
        stubRemote(Country.Usa, Single.just(result))

        repo.getLatestNews(Country.Usa).test()
        verify(mockDataBaseDs, times(1)).saveNewsToCache(Country.Usa, result)
    }

    @Test
    fun getLatestNewsErrorCache_returnsRemoteData() {
        val result = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.error(RuntimeException(randomString())))
        stubRemote(Country.Usa, Single.just(result))

        repo.getLatestNews(Country.Usa).test().assertResult(result)
    }

    @Test
    fun getLatestNewsWithCache_returnsCache() {
        val result = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.just(result))
        stubRemote(Country.Usa, Single.just(emptyList()))

        repo.getLatestNews(Country.Usa).test().assertResult(result)
    }

    /**
     * There is cached data, but not for the requested country (France). So data gets fetched from backend
     */
    @Test
    fun getLatestNewsWithCacheForAnotherCountry_returnsDataFromRemote() {
        val franceResult = listOf(generateRandomMockNews(), generateRandomMockNews())
        val usaResult = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.just(usaResult))
        stubDb(Country.France, Single.just(emptyList()))
        stubRemote(Country.France, Single.just(franceResult))

        repo.getLatestNews(Country.France).test().assertResult(franceResult)
    }

    /**
     * There is cached data, but not for the requested country (France). So data gets fetched from backend
     */
    @Test
    fun getLatestNewsWithCacheForAnotherCountry_callsRemote() {
        val franceResult = listOf(generateRandomMockNews(), generateRandomMockNews())
        val usaResult = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.just(usaResult))
        stubDb(Country.France, Single.just(emptyList()))
        stubRemote(Country.France, Single.just(franceResult))

        repo.getLatestNews(Country.France).test()

        verify(mockRemoteDs, times(1)).getLatestNews(Country.France)
    }

    @Test
    fun getLatestNewsWithCache_doesNotCallBackend() {
        val result = generateMockDomainNewsListUs()
        stubDb(Country.Usa, Single.just(result))
        stubRemote(Country.Usa, Single.just(emptyList()))

        repo.getLatestNews(Country.Usa).test()

        verifyZeroInteractions(mockRemoteDs)
    }

    private fun stubRemote(country: Country, result: Single<List<News>>) {
        whenever(mockRemoteDs.getLatestNews(country))
            .thenReturn(result)
    }

    private fun stubDb(country: Country, result: Single<List<News>>) {
        whenever(mockDataBaseDs.getCachedNews(eq(country), any()))
            .thenReturn(result)
    }
}