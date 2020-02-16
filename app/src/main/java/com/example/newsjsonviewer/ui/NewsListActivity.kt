package com.example.newsjsonviewer.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.framework.app.NewsApplication
import com.example.newsjsonviewer.ui.adapter.NewsListAdapter
import com.example.newsjsonviewer.ui.extensions.hide
import com.example.newsjsonviewer.ui.extensions.show
import com.example.newsjsonviewer.ui.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*


class NewsListActivity : AppCompatActivity() {

    private val adapter by lazy { NewsListAdapter() }

    private val viewModel by lazy { initViewModel() }

    private fun initViewModel(): NewsListViewModel {
        val repo = (application as NewsApplication).getComponent().newsRepository()
        val vm = NewsListViewModelFactory(repo).create(NewsListViewModel::class.java)

        vm.setIdlingResource(idlingResource)
        return vm
    }

    private var idlingResource: CountingIdlingResource? = getIdlingResource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        title = "Us news"

        initRecyclerView()

        observeData()

        viewModel.loadNews()
        startShimmer()
    }

    private fun initRecyclerView() {
        rvNewsList.layoutManager = LinearLayoutManager(this)
        rvNewsList.adapter = adapter
    }

    private fun observeData() {
        viewModel.newsListLiveData.observe(this, Observer { newsList ->
            stopShimmer()
            adapter.items = newsList!!
        })

        viewModel.newsListErrorLiveData.observe(this, Observer { error ->
            stopShimmer()
            Toast.makeText(this@NewsListActivity, "There was an error: $error", Toast.LENGTH_LONG)
                .show()
        })
    }

    private fun startShimmer() {
        shimmerViewContainer.show()
        shimmerViewContainer.startShimmer()
    }

    private fun stopShimmer() {
        shimmerViewContainer.stopShimmer()
        shimmerViewContainer.hide()
    }

    /**
     * Only called from test, creates and returns a new [CountingIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): CountingIdlingResource? {
        if (idlingResource == null) {
            idlingResource = CountingIdlingResource("NEWS_LIST_REMOTE_CALLS")
        }
        return idlingResource
    }

}

class NewsListViewModelFactory(private val repo: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = NewsListViewModel(repo) as T
}