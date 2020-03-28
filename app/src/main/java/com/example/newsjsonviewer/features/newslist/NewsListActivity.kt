package com.example.newsjsonviewer.features.newslist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.domain.repository.NewsRepository
import com.example.newsjsonviewer.features.newsdetail.NEWS_TO_SHOW_DETAIL_EXTRA
import com.example.newsjsonviewer.features.newsdetail.NewsDetailActivity
import com.example.newsjsonviewer.globals.BaseActivity
import com.example.newsjsonviewer.globals.utils.hide
import com.example.newsjsonviewer.globals.utils.show
import kotlinx.android.synthetic.main.activity_news_list.*
import javax.inject.Inject


class NewsListActivity : BaseActivity() {

    private val adapter by lazy { initAdapter() }

    private val viewModel by lazy { initViewModel() }

    @Inject
    protected lateinit var repo: NewsRepository

    private fun initViewModel(): NewsListViewModel {
        val vm = NewsListViewModelFactory(
            repo
        ).create(NewsListViewModel::class.java)

        vm.initIdlingResource(idlingResource)
        return vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        title = "Us news"

        initRecyclerView()

        initListeners()

        observeViewModel()

        viewModel.onEvent(NewsListEvent.ScreenLoadEvent)
    }

    private fun observeViewModel() {
        viewModel.viewStateLD.observe(this, Observer { render(it) })
        viewModel.viewEffectsLD.observe(this, Observer { takeActionOn(it) })
    }

    private fun render(viewState: NewsListViewState) {
        when(viewState) {
            NewsListViewState.Loading -> renderLoading()
            is NewsListViewState.Content -> renderContent(viewState.content)
            is NewsListViewState.Error -> renderError(viewState.message)
        }
    }

    private fun renderLoading() {
        startShimmer()
    }

    private fun renderContent(viewState: NewsListViewStateContent) {
        stopShimmer()
        swipeRefresh.isRefreshing = false
        adapter.items = viewState.newsList
    }

    private fun renderError(message: String) {
        stopShimmer()
        swipeRefresh.isRefreshing = false
        Toast.makeText(this@NewsListActivity, "There was an error: $message", Toast.LENGTH_LONG).show()
    }

    private fun takeActionOn(viewEffect: NewsListViewEffect) {
        when(viewEffect) {
            is NewsListViewEffect.LoadDetailsEffect -> loadDetails(viewEffect.news, viewEffect.imageView)
        }
    }

    private fun loadDetails(news: News, imageView: ImageView) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra(NEWS_TO_SHOW_DETAIL_EXTRA, news)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView,
            ViewCompat.getTransitionName(imageView)!!)
        startActivity(intent, options.toBundle())
    }

    /**
     * Init adapter providing it with a click listener for list element clicks
     */
    private fun initAdapter() =
        NewsListAdapter { news, imageView ->
            viewModel.onEvent(NewsListEvent.ElementClickEvent(news, imageView))
        }

    private fun initRecyclerView() {
        rvNewsList.layoutManager = LinearLayoutManager(this)
        rvNewsList.adapter = adapter
    }

    private fun initListeners() {
        swipeRefresh.setOnRefreshListener {
            adapter.items = emptyList()
            adapter.notifyDataSetChanged()
            viewModel.onEvent(NewsListEvent.ScreenReLoadEvent)
        }
    }

    private fun startShimmer() {
        shimmerViewContainer.show()
        shimmerViewContainer.startShimmer()
    }

    private fun stopShimmer() {
        shimmerViewContainer.stopShimmer()
        shimmerViewContainer.hide()
    }

}