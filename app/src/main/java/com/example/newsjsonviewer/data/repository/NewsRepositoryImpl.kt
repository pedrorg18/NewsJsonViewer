package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.data.datasource.NewsDbDataSource
import com.example.newsjsonviewer.data.datasource.NewsRemoteDataSource
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.repository.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

const val CACHED_NEWS_EXPIRATION_TIME = (120 * 1000).toLong()

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsDbDataSource: NewsDbDataSource
) : NewsRepository {

    // try to get news from local cache. If there aren't or they're expired, call remote API
    override fun getLatestNews(country: Country) =
        newsDbDataSource.getCachedNews(country, CACHED_NEWS_EXPIRATION_TIME)
            .flatMap { cachedNews ->
                if(cachedNews.isNotEmpty())
                    Single.just(cachedNews)
                else
                    // if no cache, get news from remote, if success store them in cache
                    getFromRemoteAndSaveCache(country)

            }
            // if error retrieving cache, get from backend and don't tell user
            .onErrorResumeNext {
                getFromRemoteAndSaveCache(country)
            }

    private fun getFromRemoteAndSaveCache(country: Country) =
        newsRemoteDataSource.getLatestNews(country)
            .doAfterSuccess {
                newsDbDataSource.saveNewsToCache(country, it)
                    .subscribe()
            }
}