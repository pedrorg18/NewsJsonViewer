package com.example.newsjsonviewer.framework.network

import com.example.newsjsonviewer.framework.network.model.NewsListEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRemoteService {

    @GET("/v2/top-headlines")
    fun getLatestNews(@Query("apiKey") apiKey: String, @Query("country") country: String ): Single<NewsListEntity>
}