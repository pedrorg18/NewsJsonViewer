package com.example.newsjsonviewer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.model.mapper.NewsDetailDomainToViewStateMapper
import com.example.newsjsonviewer.ui.viewstate.NewsDetailEvent
import com.example.newsjsonviewer.ui.viewstate.NewsDetailViewState

class NewsDetailViewModel : ViewModel() {

    val viewStateLiveData = MutableLiveData<NewsDetailViewState>()

    fun onEvent(event: NewsDetailEvent) {
        when(event) {
            is NewsDetailEvent.LoadDetailScreen -> onLoadDetailScreen(event.news)
        }
    }

    private fun onLoadDetailScreen(news: News) {
        viewStateLiveData.value = NewsDetailDomainToViewStateMapper().map(news)
    }

}

class NewsDetailViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = NewsDetailViewModel() as T
}