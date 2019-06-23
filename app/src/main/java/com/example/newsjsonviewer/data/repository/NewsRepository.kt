package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.domain.model.News
import io.reactivex.SingleObserver

class NewsRepository (private val newsProvider: NewsProvider) {

    fun getLatestNews(country: String, observer: SingleObserver<List<News>>) {
        newsProvider.getLatestNews(country, observer)
    }

    fun clean() {
        newsProvider.clean()
    }

}