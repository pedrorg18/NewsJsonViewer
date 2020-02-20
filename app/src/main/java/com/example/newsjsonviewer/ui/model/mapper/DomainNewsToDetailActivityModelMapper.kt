package com.example.newsjsonviewer.ui.model.mapper

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.utils.extensions.formatDdMmYyyyHhMm
import com.example.newsjsonviewer.ui.model.DetailActivityModel

class DomainNewsToDetailActivityModelMapper {

    fun map(domainNews: News) =
        with(domainNews) {
            DetailActivityModel(
                imageUrl!!,
                title,
                description!!,
                "$author · ${publishedAt!!.formatDdMmYyyyHhMm()}",
                "Source: $source",
                 content!!)
        }

}