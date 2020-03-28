package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.ui.NewsDetailActivity
import com.example.newsjsonviewer.ui.NewsListActivity

interface IAppComponent {

    fun inject(listActivity: NewsListActivity)
    fun inject(detailActivity: NewsDetailActivity)
}