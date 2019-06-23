package com.example.newsjsonviewer.data.repository

import com.example.newsjsonviewer.domain.model.News
import io.reactivex.SingleObserver

interface NewsProvider {
    fun getLatestNews(country: String, observer: SingleObserver<List<News>>)

    fun clean()
}