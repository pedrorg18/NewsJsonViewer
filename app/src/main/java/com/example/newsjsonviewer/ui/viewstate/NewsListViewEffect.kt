package com.example.newsjsonviewer.ui.viewstate

import android.widget.ImageView
import com.example.newsjsonviewer.domain.model.News


sealed class NewsListViewEffect {
    data class LoadDetailsEffect(val news: News, val imageView: ImageView) : NewsListViewEffect()
}