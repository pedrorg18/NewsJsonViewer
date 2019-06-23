package com.example.newsjsonviewer.framework.network.model

data class NewsListEntity (
    val status: String,
    val totalResults: Int,
    val articles: List<NewsEntity>)