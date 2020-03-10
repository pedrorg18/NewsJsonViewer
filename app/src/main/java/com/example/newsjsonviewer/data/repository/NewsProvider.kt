package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Single

interface NewsProvider {
    fun getLatestNews(country: String): Single<List<News>>
}