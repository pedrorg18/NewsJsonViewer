package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.features.newsdetail.NewsDetailActivity
import com.example.newsjsonviewer.features.newslist.NewsListActivity

interface IAppComponent {

    fun inject(listActivity: NewsListActivity)
    fun inject(detailActivity: NewsDetailActivity)
}