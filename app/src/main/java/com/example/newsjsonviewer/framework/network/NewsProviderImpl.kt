package com.example.newsjsonviewer.framework.network

import com.example.newsjsonviewer.data.repository.NewsProvider
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.mapper.NewsNetworkToDomainMapper
import com.example.newsjsonviewer.framework.network.model.NewsListEntity
import io.reactivex.Single
import javax.inject.Inject

class NewsProviderImpl @Inject constructor(private val networkManager: NetworkManager) : NewsProvider {

    override fun getLatestNews(country: String): Single<List<News>> {
        val apiService = networkManager.getClient()
            .create(NewsApiInterface::class.java)

        return apiService.getLatestNews(NEWSAPI_API_KEY, country)
            .map { newsListEntity ->
                if(newsListEntity.status == NEWSAPI_STATUS_OK)
                    mapNetworkToDomainModelAndFilter(newsListEntity)
                else
                    throw IllegalStateException("Received error code from server: ${newsListEntity.status}")
            }
    }

    // It filters news which don't have certain fields
    private fun mapNetworkToDomainModelAndFilter(newsListEntity: NewsListEntity) =
        newsListEntity.articles.map {
            NewsNetworkToDomainMapper().map(it)
        }.filter {
            with(it) {
                description != null && imageUrl != null && author != null && source != null && publishedAt != null
                        && content != null
            }
        }

}