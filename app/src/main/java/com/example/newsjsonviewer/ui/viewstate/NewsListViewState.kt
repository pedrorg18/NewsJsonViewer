package com.example.newsjsonviewer.ui.viewstate

import com.example.newsjsonviewer.domain.model.News

sealed class NewsListViewState {

    object Loading : NewsListViewState()
    data class Content(val content: NewsListViewStateContent) : NewsListViewState()
    data class Error(val message: String) : NewsListViewState()
}

data class NewsListViewStateContent(val newsList: List<NewsListElementViewStateContent>)

data class NewsListElementViewStateContent(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    // Must have reference to domain object in order to generate detail ViewState when clicked on
    val domainObject: News
)

