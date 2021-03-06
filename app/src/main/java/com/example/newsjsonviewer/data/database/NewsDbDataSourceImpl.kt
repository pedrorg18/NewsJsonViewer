package com.example.newsjsonviewer.data.database

import com.example.newsjsonviewer.data.database.mapper.NewsDbToDomainMapper
import com.example.newsjsonviewer.data.database.model.CachedNews
import com.example.newsjsonviewer.data.datasource.NewsDbDataSource
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class NewsDbDataSourceImpl @Inject constructor(private val newsDatabase: NewsDatabase)
    : NewsDbDataSource {

    override fun getCachedNews(
        country: Country,
        cachedNewsExpirationTime: Long
    ): Single<List<News>> =
        newsDatabase.cachedNewsDao().findCachedNewsAndDbNewsByCountry(country.name)
            .flatMap {
                if (it.cachedNews.timestamp + cachedNewsExpirationTime > System.currentTimeMillis())
                    Maybe.just(
                        it.dbNewsList.map { dbNews ->
                            NewsDbToDomainMapper().mapToDomain(dbNews)
                        }
                    )
                else
                    Maybe.just(emptyList())
            }
            .switchIfEmpty(
                Single.just(listOf())
            )

    override fun saveNewsToCache(country: Country, news: List<News>) =
        Completable.create {
                val success = newsDatabase.cachedNewsDao().insertCachedNewsAndNewsList(
                    CachedNews(
                        country.name,
                        System.currentTimeMillis()
                    ),
                    news.map { newsList ->
                        NewsDbToDomainMapper().mapToDb(newsList, country.name)
                    }
                )
            if (success) it.onComplete()
            else it.onError(RuntimeException("There was an error inserting news into the database"))
        }

}