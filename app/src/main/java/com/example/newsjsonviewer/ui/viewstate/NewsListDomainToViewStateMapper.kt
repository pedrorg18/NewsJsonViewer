package com.example.newsjsonviewer.ui.viewstate

import com.example.newsjsonviewer.domain.model.News

class NewsListDomainToViewStateMapper {

    fun map(domainList: List<News>) =
        NewsListViewState.Content(
            NewsListViewStateContent(
                domainList.map {
                    NewsListElementViewStateContent(
                        it.title,
                        it.description,
                        it.imageUrl,
                        it
                    )
                }
            )
        )
}