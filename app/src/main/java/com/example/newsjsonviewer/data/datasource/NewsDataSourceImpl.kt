package com.example.newsjsonviewer.data.datasource

import com.example.newsjsonviewer.data.network.NEWSAPI_API_KEY
import com.example.newsjsonviewer.data.network.NEWSAPI_STATUS_OK
import com.example.newsjsonviewer.data.network.NetworkManager
import com.example.newsjsonviewer.data.network.NewsApiInterface
import com.example.newsjsonviewer.data.network.mapper.NewsDomainToNetworkMapper
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.data.network.mapper.NewsNetworkToDomainMapper
import com.example.newsjsonviewer.data.network.model.NewsListEntity
import com.example.newsjsonviewer.domain.model.Country
import io.reactivex.Single
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(private val networkManager: NetworkManager) :
    NewsDataSource {

    override fun getLatestNews(country: Country): Single<List<News>> {
        val apiService = networkManager.getClient()
            .create(NewsApiInterface::class.java)

        return apiService.getLatestNews(NEWSAPI_API_KEY,
                NewsDomainToNetworkMapper().mapCountryCode(country))
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