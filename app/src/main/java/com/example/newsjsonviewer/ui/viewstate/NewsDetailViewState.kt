package com.example.newsjsonviewer.ui.viewstate

sealed class NewsDetailViewState {

//    object Loading : NewsDetailViewState()
    data class Content(val content: NewsDetailViewStateContent) : NewsDetailViewState()
//    data class Error(val message: String) : NewsDetailViewState()
}

data class NewsDetailViewStateContent(
    val imageUrl: String,
    val title: String,
    val subTitle: String,
    val authorAndDate: String,
    val source: String,
    val content: String
)

