package com.example.newsjsonviewer.mock

import android.annotation.SuppressLint
import android.util.Log
import com.example.newsjsonviewer.di.IAppComponent
import com.example.newsjsonviewer.framework.app.NewsApplication
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import java.net.InetAddress

class NewsMockApplication : NewsApplication() {


    private lateinit var webServer: MockWebServer

    override fun onCreate() {
        super.onCreate()
        launchMockWebServer()
    }

    override fun getComponent(): IAppComponent {
        if(applicationComponent == null)
            applicationComponent = DaggerMockApplicationComponent.builder()
                .mockApplicationModule(MockApplicationModule(this))
                .mockNetworkModule(MockNetworkModule())
                .build()

        return applicationComponent!!
    }

    @SuppressLint("CheckResult")
    private fun launchMockWebServer() {
      Completable.fromAction {
          webServer = MockWebServer()
          webServer.start(InetAddress.getByName("localhost"), 8081)

          webServer.dispatcher = MockServerDispatcher(
              this
          ).RequestDispatcher()
      }
          .subscribeOn(Schedulers.io())
          .subscribe({
            Log.i("SERVER:::", "server OK")
        },
            {
                it.printStackTrace()
                Log.e("SERVER:::", "ERROR loading server: ${it.message}")
            })

    }
}