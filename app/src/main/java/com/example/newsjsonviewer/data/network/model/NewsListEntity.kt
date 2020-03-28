package com.example.newsjsonviewer.data.network.model

data class NewsListEntity (
    val status: String,
    val totalResults: Int,
    val articles: List<NewsEntity>)