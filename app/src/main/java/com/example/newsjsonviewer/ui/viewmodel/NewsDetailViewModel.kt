package com.example.newsjsonviewer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.model.DetailActivityModel
import javax.inject.Inject

class NewsDetailViewModel(private var repository: NewsRepository) : ViewModel() {

    val newsDetailLiveData = MutableLiveData<DetailActivityModel>()

    /**
     * Receives one news object in Domain data format, converts it to View format
     */
    fun mapNewsToViewFormat(news: News) {
        newsDetailLiveData.value = repository.mapDomainNewsToDetailModel(news)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clean()
    }
}