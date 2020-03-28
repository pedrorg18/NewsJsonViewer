package com.example.newsjsonviewer.features.newsdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.globals.BaseViewModel

class NewsDetailViewModel : BaseViewModel() {

    val viewStateLiveData = MutableLiveData<NewsDetailViewState>()

    fun onEvent(event: NewsDetailEvent) {
        when(event) {
            is NewsDetailEvent.LoadDetailScreen -> onLoadDetailScreen(event.news)
        }
    }

    private fun onLoadDetailScreen(news: News) {
        viewStateLiveData.value = NewsDetailDomainToViewStateMapper()
            .map(news)
    }

}

class NewsDetailViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = NewsDetailViewModel() as T
}