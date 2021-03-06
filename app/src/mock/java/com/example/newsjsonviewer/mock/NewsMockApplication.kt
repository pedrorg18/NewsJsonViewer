package com.example.newsjsonviewer.mock

import android.annotation.SuppressLint
import android.util.Log
import com.example.newsjsonviewer.di.IAppComponent
import com.example.newsjsonviewer.globals.NewsApplication
import com.example.newsjsonviewer.mock.module.MockApplicationModule
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import java.net.InetAddress

@SuppressLint("Registered")
open class NewsMockApplication : NewsApplication() {


    private lateinit var webServer: MockWebServer

    override fun onCreate() {
        super.onCreate()
        launchMockWebServer()
    }

    override fun getComponent(): IAppComponent {
        if(applicationComponent == null)
            applicationComponent = DaggerMockApplicationComponent.builder()
                .mockApplicationModule(
                    MockApplicationModule(this)
                )
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