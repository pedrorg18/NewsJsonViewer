package com.example.newsjsonviewer.domain.usecases

import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor (private var repository: NewsRepository) {
    fun get(country: Country, cache: Boolean) = repository.getLatestNews(country, cache)
}