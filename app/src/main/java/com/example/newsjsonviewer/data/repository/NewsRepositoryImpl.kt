package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.data.datasource.NewsRemoteDataSource
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {

    override fun getLatestNews(country: Country) =
        newsRemoteDataSource.getLatestNews(country)
}