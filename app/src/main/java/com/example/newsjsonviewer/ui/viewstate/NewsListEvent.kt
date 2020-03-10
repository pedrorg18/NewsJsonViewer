package com.example.newsjsonviewer.ui.viewstate

import android.widget.ImageView
import com.example.newsjsonviewer.domain.model.News


sealed class NewsListEvent {
    object ScreenLoadEvent : NewsListEvent()
    data class ElementClickEvent(val news: News, val imageView: ImageView) : NewsListEvent()
    object ScreenReLoadEvent : NewsListEvent()
}