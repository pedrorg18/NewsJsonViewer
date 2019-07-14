package com.example.newsjsonviewer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.NewsProviderImpl
import com.example.newsjsonviewer.ui.model.DetailActivityModel

class NewsDetailViewModel : ViewModel() {

    val newsDetailLiveData = MutableLiveData<DetailActivityModel>()

    private val repository = NewsRepository(NewsProviderImpl())

    fun mapNews(news: News) {
        newsDetailLiveData.value = repository.mapDomainNewsToDetailModel(news)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clean()
    }
}