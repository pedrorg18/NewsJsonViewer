package com.example.newsjsonviewer.data.network.mapper

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.data.network.model.NewsEntity

class NewsNetworkToDomainMapper {

    /**
     * Maps Json model to domain model.
     */
    fun map(netEntity: NewsEntity) =
        with(netEntity) {
            News(title,
                description,
                urlToImage,
                author,
                source?.name,
                publishedAt,
                content)
        }

}