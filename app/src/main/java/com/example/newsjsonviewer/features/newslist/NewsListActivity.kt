package com.example.newsjsonviewer.features.newslist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.domain.usecases.GetNewsUseCase
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
    protected lateinit var getNewsUseCase: GetNewsUseCase

    private fun initViewModel(): NewsListViewModel {
        val vm = NewsListViewModelFactory(getNewsUseCase)
            .create(NewsListViewModel::class.java)

        vm.initIdlingResource(idlingResource)
        return vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

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
        rvNewsList.hide()
        startShimmer()
    }

    private fun renderContent(viewState: NewsListViewStateContent) {
        rvNewsList.show()
        stopShimmer()
        swipeRefresh.isRefreshing = false
        title = viewState.pageTitle
        adapter.items = viewState.newsList
        renderCountryPanel(viewState.countrySelectionPanel)
    }

    private fun renderCountryPanel(countrySelectionPanel: CountrySelectionPanel?) {
        countrySelectionPanel?.let {
            val dialog = CountryListDialogFragment(it.countryList) { selectedCountry ->
                viewModel.onEvent(NewsListEvent.DoChangeCountryEvent(selectedCountry))
            }
            dialog.show(supportFragmentManager, "CountryDialogFragment")
        }
    }

    private fun renderError(message: String) {
        rvNewsList.show()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.news_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_change_country -> {
                viewModel.onEvent(NewsListEvent.ChangeCountryClickEvent)
                true
            }
            else -> super.onOptionsItemSelected(item)
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