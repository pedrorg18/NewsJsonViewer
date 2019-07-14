package com.example.newsjsonviewer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.COUNTRY_CODE_US
import com.example.newsjsonviewer.framework.network.NewsProviderImpl
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class NewsListViewModel : ViewModel() {

    val newsListLiveData = MutableLiveData<List<News>>()
    val newsListErrorLiveData = MutableLiveData<String>()

    private val repository = NewsRepository(NewsProviderImpl())

    /**
     * Loads general news from the US. It filters news which don't have description or image
     */
    fun loadNews() {
        repository.getLatestNews(COUNTRY_CODE_US, object: SingleObserver<List<News>> {
            override fun onSuccess(news: List<News>) {
                newsListLiveData.value = news.filter {
                    it.content != null && it.imageUrl != null
                }
            }

            override fun onError(e: Throwable) {
                newsListErrorLiveData.value = e.message ?: "Unknown error"
            }

            override fun onSubscribe(d: Disposable) {}
        })
    }

    override fun onCleared() {
        super.onCleared()
        repository.clean()
    }
}