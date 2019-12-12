package com.example.newsjsonviewer.framework.network

import com.example.newsjsonviewer.framework.network.model.NewsListEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val GET_LATEST_NEWS_PATH = "/v2/top-headlines"
interface NewsRemoteService {

    @GET(GET_LATEST_NEWS_PATH)
    fun getLatestNews(@Query("apiKey") apiKey: String, @Query("country") country: String ): Single<NewsListEntity>
}