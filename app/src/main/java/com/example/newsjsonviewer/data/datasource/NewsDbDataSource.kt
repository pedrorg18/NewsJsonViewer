package com.example.newsjsonviewer.data.datasource

import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Completable
import io.reactivex.Single

interface NewsDbDataSource {
    /**
     * Gets cached news stored in data base.
     *
     * @param country the country of the expected cached news
     * @param cachedNewsExpirationTime the expiration time for these news.
     * If they are older than this, an empty list will be returned
     */
    fun getCachedNews(
        country: Country,
        cachedNewsExpirationTime: Long
    ): Single<List<News>>

    /**
     * Saves news into database associated to the search criteria to fetch them (the country)
     */
    fun saveNewsToCache(country: Country, news: List<News>): Completable
}