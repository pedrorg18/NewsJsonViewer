package com.example.newsjsonviewer.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.ui.adapter.NewsListAdapter
import com.example.newsjsonviewer.ui.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { NewsListAdapter() }

    private val viewModel by lazy { ViewModelProviders.of(this).get(NewsListViewModel::class.java)}

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
        viewModel.loadNews()

        viewModel.newsListLiveData.observe(this, Observer { newsList ->
            adapter.items = newsList!!
        })

        viewModel.newsListErrorLiveData.observe(this, Observer { error ->
            Toast.makeText(this@MainActivity, "There was an error: $error", Toast.LENGTH_LONG).show()
        })

    }

}
