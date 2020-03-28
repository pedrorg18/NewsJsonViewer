package com.example.newsjsonviewer.features.newslist

import android.widget.ImageView
import com.example.newsjsonviewer.domain.model.News


sealed class NewsListViewEffect {
    data class LoadDetailsEffect(val news: News, val imageView: ImageView) : NewsListViewEffect()
}