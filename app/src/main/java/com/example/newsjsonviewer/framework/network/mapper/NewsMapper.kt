package com.example.newsjsonviewer.framework.network.mapper

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.model.NewsEntity

class NewsMapper {

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