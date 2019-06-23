package com.example.newsjsonviewer.framework.network

import com.example.newsjsonviewer.data.repository.NewsProvider
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.mapper.NewsMapper
import com.example.newsjsonviewer.framework.network.model.NewsListEntity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsProviderImpl : NewsProvider {

    private val compositeDisposable = CompositeDisposable()

    override fun getLatestNews(country: String, observer: SingleObserver<List<News>>) {
        val apiService = NetworkManager().getClient()
            .create(NewsRemoteService::class.java)

        val observable = apiService.getLatestNews(NEWSAPI_API_KEY, country)
        val disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNewsSuccess(observer), onNewsError(observer))

        compositeDisposable.addAll(disposable)
    }

    private fun onNewsSuccess(observer: SingleObserver<List<News>>) = { newsListEntity: NewsListEntity ->
        if(newsListEntity.status == NEWSAPI_STATUS_OK) {
            observer.onSuccess(newsListEntity.articles.map {
                NewsMapper().map(it)
            })
        } else {
            observer.onError(IllegalStateException("Received error code from server: ${newsListEntity.status}"))
        }
    }

    private fun onNewsError(observer: SingleObserver<List<News>>) = { error: Throwable ->
        observer.onError(error)
    }

    override fun clean() {
        compositeDisposable.dispose()
    }
}