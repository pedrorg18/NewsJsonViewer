package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.model.mapper.DomainNewsToDetailActivityModelMapper
import io.reactivex.SingleObserver

class NewsRepository (private val newsProvider: NewsProvider) {

    fun getLatestNews(country: String, observer: SingleObserver<List<News>>) {
        newsProvider.getLatestNews(country, observer)
    }

    fun mapDomainNewsToDetailModel(news: News) =
        DomainNewsToDetailActivityModelMapper().map(news)

    fun clean() {
        newsProvider.clean()
    }

}