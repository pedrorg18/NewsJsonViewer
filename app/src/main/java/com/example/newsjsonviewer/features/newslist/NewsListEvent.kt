package com.example.newsjsonviewer.features.newslist

import android.widget.ImageView
import com.example.newsjsonviewer.domain.model.News


sealed class NewsListEvent {
    object ScreenLoadEvent : NewsListEvent()
    data class ElementClickEvent(val news: News, val imageView: ImageView) : NewsListEvent()
    object ScreenReLoadEvent : NewsListEvent()
}