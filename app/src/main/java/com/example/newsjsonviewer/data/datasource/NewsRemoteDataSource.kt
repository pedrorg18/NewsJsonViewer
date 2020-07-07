package com.example.newsjsonviewer.data.datasource

import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Single

interface NewsRemoteDataSource {
    fun getLatestNews(country: Country): Single<List<News>>
}