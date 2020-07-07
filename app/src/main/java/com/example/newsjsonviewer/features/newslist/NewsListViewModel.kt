package com.example.newsjsonviewer.features.newslist

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.domain.usecases.GetNewsUseCase
import com.example.newsjsonviewer.globals.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsListViewModel (private var getNewsUseCase: GetNewsUseCase,
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

    private var selectedCountry: Country = Country.Usa


    fun onEvent(event: NewsListEvent) {
        when (event) {
            is NewsListEvent.ScreenLoadEvent -> onScreenLoad()
            is NewsListEvent.ScreenReLoadEvent -> onScreenReLoad()
            is NewsListEvent.ElementClickEvent -> onElementClick(event.news, event.imageView)
            is NewsListEvent.ChangeCountryClickEvent -> onChangeCountryClick()
            is NewsListEvent.DoChangeCountryEvent -> onDoChangeCountry(event.country)
        }
    }

    private fun onScreenLoad() {
        doLoadNews()
    }

    private fun onScreenReLoad() {
        doLoadNews()
    }

    private fun onElementClick(
        news: News,
        imageView: ImageView
    ) {
        viewEffectsLD.value = NewsListViewEffect.LoadDetailsEffect(news, imageView)
    }

    private fun onChangeCountryClick() {
        currentViewState = NewsListViewState.Content(
            (currentViewState as NewsListViewState.Content).content.copy(
                countrySelectionPanel = NewsListDomainToViewStateMapper()
                    .mapCountryPanel(selectedCountry, Country.all())
            )

        )
    }

    private fun onDoChangeCountry(countryName: String) {
        selectedCountry = Country.all().find {
            it.name == countryName
        } ?: throw IllegalArgumentException("Invalid country name $countryName")
        doLoadNews()
    }

    private fun doLoadNews() {
        currentViewState = NewsListViewState.Loading

        loadNews(
            { newsList ->
                currentViewState = NewsListDomainToViewStateMapper()
                    .mapList(newsList, selectedCountry)
            },
            { error ->
                currentViewState = NewsListViewState.Error(error.message!!)
            }
        )
    }

    /**
     * Loads general news from the selected country
     */
    private fun loadNews(
        successFunction: (List<News>) -> Unit,
        errorFunction: (Throwable) -> Unit
    ) {
        incrementIdlingResource()
        compositeDisposable.add(
            getNewsUseCase.get(selectedCountry)
                .compose(applySchedulers())
                .subscribe(
                    {
                        successFunction(it)
                        decrementIdlingResource()
                    },
                    {
                        errorFunction(it)
                        decrementIdlingResource()
                    })
        )
    }

    private fun initialViewState() =
        NewsListViewState.Content(
            NewsListViewStateContent(
                "",
                emptyList(),
                null
            )
        )

    private fun <T> applySchedulers() = SingleTransformer<T, T> {
        it
            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
    }

}

class NewsListViewModelFactory(private var getNewsUseCase: GetNewsUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = NewsListViewModel(
        getNewsUseCase
    ) as T
}