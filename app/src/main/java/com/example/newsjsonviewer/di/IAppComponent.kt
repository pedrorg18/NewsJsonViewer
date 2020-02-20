package com.example.newsjsonviewer.di

import com.example.newsjsonviewer.data.repository.NewsRepository

interface IAppComponent {

    fun newsRepository(): NewsRepository
}