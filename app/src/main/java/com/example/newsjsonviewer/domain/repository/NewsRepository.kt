package com.example.newsjsonviewer.domain.repository

import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import io.reactivex.Single

interface NewsRepository {

    /**
     * Gets latest news for a given country. When data comes from backend, save it into database cache
     *
     * @param cache if true, check if there is a non expired cache before going to backend
     */
    fun getLatestNews(
        country: Country,
        cache: Boolean
    ): Single<List<News>>
}