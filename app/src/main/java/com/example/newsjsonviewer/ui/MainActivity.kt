package com.example.newsjsonviewer.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.ui.adapter.NewsListAdapter
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.COUNTRY_CODE_US
import com.example.newsjsonviewer.framework.network.NewsProviderImpl
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { NewsListAdapter() }
    private val repository by lazy { NewsRepository(NewsProviderImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        layoutData()
    }

    private fun initRecyclerView() {
        rvNewsList.layoutManager = LinearLayoutManager(this)
        rvNewsList.adapter = adapter
    }

    private fun layoutData() {
        repository.getLatestNews(COUNTRY_CODE_US, object: SingleObserver<List<News>> {
            override fun onSuccess(news: List<News>) {
                adapter.items = news
            }

            override fun onError(e: Throwable) {
                Toast.makeText(this@MainActivity, "There was an error: ${e.message}", Toast.LENGTH_LONG).show()
            }

            override fun onSubscribe(d: Disposable) {}
        })
    }

    override fun onDestroy() {
        repository.clean()
        super.onDestroy()
    }
}
