package com.example.newsjsonviewer.data.datasource

import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Single

interface NewsDataSource {
    fun getLatestNews(country: String): Single<List<News>>
}