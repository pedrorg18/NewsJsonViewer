package com.example.newsjsonviewer.test.mock

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.data.network.mapper.NewsNetworkToDomainMapper
import com.example.newsjsonviewer.data.network.model.NewsListEntity
import com.example.newsjsonviewer.test.mock.MockGenerator.Companion.getJsonEntityFromFileName

/**
 * Generates mock entities from json, for Unit Tests only
 */


/**
 * Mock network news list model from US
 */
fun generateMockNewsListUS(): NewsListEntity {
    return getJsonEntityFromFileName("mock_us_news_list.json")
}

/**
 * First domain mapped element from mock US list
 */
fun generateFirstMockDomainNewsFromUSList(): News {
    val networkNewsList = generateMockNewsListUS()
    return NewsNetworkToDomainMapper().map(networkNewsList.articles.first())
}
