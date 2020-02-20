package com.example.newsjsonviewer.mock

import com.example.newsjsonviewer.data.repository.NewsProvider
import com.example.newsjsonviewer.di.ApplicationModule
import com.example.newsjsonviewer.framework.network.NetworkManager
import com.example.newsjsonviewer.framework.network.NewsProviderImpl
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class MockApplicationModule(private val mockApp: NewsMockApplication) {
//    : ApplicationModule() {

    private val REQUEST_TIMEOUT = 60

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://localhost:8080/")
            .build()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideNewsProvider(networkManager: NetworkManager): NewsProvider = NewsProviderImpl(networkManager)
}