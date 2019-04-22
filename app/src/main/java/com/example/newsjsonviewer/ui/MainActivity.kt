package com.example.newsjsonviewer.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.ui.adapter.NewsListAdapter
import com.example.newsjsonviewer.ui.domain.model.News
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    private val FAKE_IMAGE_URL = "http://something.com/image"

    private val adapter by lazy { NewsListAdapter() }

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

    // TODO Mock data
    private fun layoutData() {
        adapter.items = listOf(News("There was a robbery somewhere", LOREM_IPSUM, FAKE_IMAGE_URL),
            News("Unimportant news", LOREM_IPSUM, FAKE_IMAGE_URL),
            News("Random guy won the lottery", LOREM_IPSUM, FAKE_IMAGE_URL))
    }
}
