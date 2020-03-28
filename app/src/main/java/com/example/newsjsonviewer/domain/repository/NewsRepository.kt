package com.example.newsjsonviewer.domain.repository

import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Single

interface NewsRepository {
    fun getLatestNews(country: String): Single<List<News>>
}