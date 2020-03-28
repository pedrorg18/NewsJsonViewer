package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.data.datasource.NewsDataSource
import com.example.newsjsonviewer.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsDataSource: NewsDataSource) :
    NewsRepository {

    override fun getLatestNews(country: String) =
        newsDataSource.getLatestNews(country)

}