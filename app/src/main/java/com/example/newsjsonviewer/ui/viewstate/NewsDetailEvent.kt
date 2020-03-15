package com.example.newsjsonviewer.ui.viewstate

import com.example.newsjsonviewer.domain.model.News

sealed class NewsDetailEvent {

    data class LoadDetailScreen(val news: News) : NewsDetailEvent()
}