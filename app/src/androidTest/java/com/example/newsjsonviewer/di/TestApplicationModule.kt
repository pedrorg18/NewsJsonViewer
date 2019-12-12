package com.example.newsjsonviewer.di

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


class TestApplicationModule(application: Application) : ApplicationModule() {

    @Provides
    @Singleton
    override fun getRetrofit(client: OkHttpClient): Retrofit? {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://localhost:8080/")
            .build()
    }

}