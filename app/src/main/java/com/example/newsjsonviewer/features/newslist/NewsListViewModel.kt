package com.example.newsjsonviewer.features.newslist

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.COUNTRY_CODE_US
import com.example.newsjsonviewer.globals.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsListViewModel (private var repository: NewsRepository,
                         private val subscriberScheduler: Scheduler = Schedulers.io(),
                         private val observerScheduler: Scheduler = AndroidSchedulers.mainThread()
)
    : BaseViewModel() {

    val viewStateLD = MutableLiveData<NewsListViewState>()
    val viewEffectsLD = MutableLiveData<NewsListViewEffect>()

    private var currentViewState: NewsListViewState = initialViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }


    fun onEvent(event: NewsListEvent) {
        when (event) {
            is NewsListEvent.ScreenLoadEvent -> onScreenLoad()
            is NewsListEvent.ScreenReLoadEvent -> onScreenReLoad()
            is NewsListEvent.ElementClickEvent -> onElementClick(event.news, event.imageView)
        }
    }

    private fun onScreenLoad() {
        doLoadNews()
    }

    private fun onScreenReLoad() {
        doLoadNews()
    }

    private fun doLoadNews() {
        currentViewState = NewsListViewState.Loading

        loadNews(
            { newsList ->
                currentViewState = NewsListDomainToViewStateMapper()
                    .map(newsList)
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
                .subscribeOn(subscriberScheduler)
                .observeOn(observerScheduler)
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
            NewsListViewStateContent(
                emptyList()
            )
        )

}

class NewsListViewModelFactory(private val repo: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = NewsListViewModel(
        repo
    ) as T
}