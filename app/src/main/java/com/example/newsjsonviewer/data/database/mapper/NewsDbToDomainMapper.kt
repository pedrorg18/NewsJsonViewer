package com.example.newsjsonviewer.data.database.mapper

import com.example.newsjsonviewer.data.database.model.DbNews
import com.example.newsjsonviewer.domain.model.News
import java.util.*

class NewsDbToDomainMapper {

    fun mapToDomain(dbModel: DbNews): News =
        with(dbModel) {
            News(
                title,
                description,
                imageUrl,
                author,
                source,
                publishedAt?.let { Date(publishedAt) },
                content
            )
        }

    fun mapToDb(domainNews: News, country: String? = null) =
        with(domainNews) {
            DbNews(
                title,
                description,
                imageUrl,
                author,
                source,
                publishedAt?.time,
                content,
                country
            )
        }
}