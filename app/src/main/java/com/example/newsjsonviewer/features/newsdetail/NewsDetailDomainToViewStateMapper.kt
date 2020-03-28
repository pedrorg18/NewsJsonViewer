package com.example.newsjsonviewer.features.newsdetail

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.utils.extensions.formatDdMmYyyyHhMm

class NewsDetailDomainToViewStateMapper {

    fun map(domainNews: News) =
        with(domainNews) {
            NewsDetailViewState.Content(
                NewsDetailViewStateContent(
                    imageUrl!!,
                    title,
                    description!!,
                    "$author · ${publishedAt!!.formatDdMmYyyyHhMm()}",
                    "Source: $source",
                    content!!
                )
            )
        }

}