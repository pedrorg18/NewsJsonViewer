package com.example.newsjsonviewer.features.newsdetail

import com.example.newsjsonviewer.domain.model.News

sealed class NewsDetailEvent {

    data class LoadDetailScreen(val news: News) : NewsDetailEvent()
}