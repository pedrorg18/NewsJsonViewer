package com.example.newsjsonviewer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.COUNTRY_CODE_US
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class NewsListViewModel(private var repository: NewsRepository) : ViewModel() {


    val newsListLiveData = MutableLiveData<List<News>>()
    val newsListErrorLiveData = MutableLiveData<String>()

    private var idlingResource: CountingIdlingResource? = null

    /**
     * Loads general news from the US
     */
    fun loadNews() {
        idlingResource?.increment()
        repository.getLatestNews(COUNTRY_CODE_US, object: SingleObserver<List<News>> {
            override fun onSuccess(news: List<News>) {
                newsListLiveData.value = news
                idlingResource?.decrement()
            }

            override fun onError(e: Throwable) {
                newsListErrorLiveData.value = e.message ?: "Unknown error"
                idlingResource?.decrement()
            }

            override fun onSubscribe(d: Disposable) {}
        })
    }

    override fun onCleared() {
        super.onCleared()
        repository.clean()
    }

    fun setIdlingResource(idlingResource: CountingIdlingResource?) {
        this.idlingResource = idlingResource
    }
}