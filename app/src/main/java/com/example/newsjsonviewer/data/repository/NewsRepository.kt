package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.model.mapper.DomainNewsToDetailActivityModelMapper
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsProvider: NewsProvider) {

    fun getLatestNews(country: String) =
        newsProvider.getLatestNews(country)

    fun mapDomainNewsToDetailModel(news: News) =
        DomainNewsToDetailActivityModelMapper().map(news)

}