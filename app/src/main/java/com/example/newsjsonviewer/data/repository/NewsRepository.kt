package com.example.newsjsonviewer.data.repository

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsProvider: NewsProvider) {

    fun getLatestNews(country: String) =
        newsProvider.getLatestNews(country)

}