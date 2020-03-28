package com.example.newsjsonviewer.domain.usecases

import com.example.newsjsonviewer.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor (private var repository: NewsRepository) {
    fun get(country: String) = repository.getLatestNews(country)
}