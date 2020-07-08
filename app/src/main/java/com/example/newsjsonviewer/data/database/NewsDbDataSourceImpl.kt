package com.example.newsjsonviewer.data.database

import com.example.newsjsonviewer.data.datasource.NewsDbDataSource
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Single

class NewsDbDataSourceImpl : NewsDbDataSource {

    override fun getCachedNews(
        country: Country,
        cachedNewsExpirationTime: Long
    ): Single<List<News>> =
        // FIXME mock, implement real DB fetch
        Single.just(
            listOf(
                mockNews(1),
                mockNews(2)
            )
        )

    override fun saveNewsToCache(country: Country, news: List<News>) {

    }

    private fun mockNews(mockId: Int) =
        News(
            "title$mockId",
            "desc$mockId",
            "image$mockId",
            "author$mockId",
            "src$mockId",
            null,
            null
        )
}