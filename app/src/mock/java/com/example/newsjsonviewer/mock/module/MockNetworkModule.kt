package com.example.newsjsonviewer.mock.module

import com.example.newsjsonviewer.data.datasource.NewsRemoteDataSource
import com.example.newsjsonviewer.data.network.NetworkManager
import com.example.newsjsonviewer.data.network.NewsRemoteDataSourceImpl
import com.example.newsjsonviewer.di.module.backendBaseUrlKey
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val mockWebServerBaseUrl = "http://localhost:8081/"

@Module
class MockNetworkModule {

    private val REQUEST_TIMEOUT = 60


    @Provides
    @Named(backendBaseUrlKey)
    fun provideBackendBaseUrl() = mockWebServerBaseUrl

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(networkManager: NetworkManager): NewsRemoteDataSource =
        NewsRemoteDataSourceImpl(
            networkManager
        )

    @Provides
    @Singleton
    fun getRetrofit(
        client: OkHttpClient,
        @Named(backendBaseUrlKey) baseUrl: String
    ) =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        // Uncomment to add delay to HTTP calls, for example to test Espresso against flakiness
//            .addInterceptor { chain ->
//                val request: Request = chain.request()
//                var response: Response? = null
//                try {
//                    SystemClock.sleep(10000)
//                    response = chain.proceed(request)
//                } catch (e: Exception) {
//                    Log.e("intercept", "Request is not successful")
//                }
//                response!!
//            }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }
}