package com.example.newsjsonviewer.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject


class NetworkManager @Inject constructor(private var retrofit: Retrofit,
                                         private var okHttpClient: OkHttpClient) {

    fun getClient(): Retrofit {
        return retrofit
    }

}