package com.example.newsjsonviewer.ui.viewmodel

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.COUNTRY_CODE_US
import com.example.newsjsonviewer.ui.viewstate.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsListViewModel(private var repository: NewsRepository) : ViewModel() {

    val viewStateLD = MutableLiveData<NewsListViewState>()
    val viewEffectsLD = MutableLiveData<NewsListViewEffect>()

    private var currentViewState: NewsListViewState = initialViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }

    private val compositeDisposable = CompositeDisposable()

    private var idlingResource: CountingIdlingResource? = null


    fun onEvent(event: NewsListEvent) {
        when (event) {
            is NewsListEvent.ScreenLoadEvent -> onScreenLoad()
            is NewsListEvent.ElementClickEvent -> onElementClick(event.news, event.imageView)
        }
    }

    private fun onScreenLoad() {
        currentViewState = NewsListViewState.Loading

        loadNews(
            { newsList ->
                currentViewState = NewsListDomainToViewStateMapper().map(newsList)
            },
            { error ->
                currentViewState = NewsListViewState.Error(error.message!!)
            }
        )
    }

    private fun onElementClick(
        news: News,
        imageView: ImageView
    ) {
        viewEffectsLD.value = NewsListViewEffect.LoadDetailsEffect(news, imageView)
    }

    /**
     * Loads general news from the US
     */
    private fun loadNews(
        successFunction: (List<News>) -> Unit,
        errorFunction: (Throwable) -> Unit
    ) {
        idlingResource?.increment()
        compositeDisposable.add(
            repository.getLatestNews(COUNTRY_CODE_US)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        successFunction(it)
                        idlingResource?.decrement()
                    },
                    {
                        errorFunction(it)
                        idlingResource?.decrement()
                    })
        )
    }

    private fun initialViewState() =
        NewsListViewState.Content(
            NewsListViewStateContent(emptyList())
        )


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun setIdlingResource(idlingResource: CountingIdlingResource?) {
        this.idlingResource = idlingResource
    }
}