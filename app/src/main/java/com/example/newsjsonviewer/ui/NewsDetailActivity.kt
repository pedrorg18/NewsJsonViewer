package com.example.newsjsonviewer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.image.loadImage
import com.example.newsjsonviewer.ui.model.DetailActivityModel
import com.example.newsjsonviewer.ui.viewmodel.NewsDetailViewModel
import kotlinx.android.synthetic.main.activity_news_detail.*

const val NEWS_TO_SHOW_DETAIL_EXTRA = "news_to_show_detail"

class NewsDetailActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(NewsDetailViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        initAndObserveViewModel()
    }

    private fun initAndObserveViewModel() {
        viewModel.newsDetailLiveData.observe(this, Observer { news ->
            layoutNews(news)
        })

        // Get news object form list screen, adapt it to detail
        viewModel.mapNewsToViewFormat(intent.getSerializableExtra(NEWS_TO_SHOW_DETAIL_EXTRA) as News)
    }

    private fun layoutNews(news: DetailActivityModel) {
        with(news) {
            loadImage(ivDetail, news.imageUrl)
            tvDetailTitle.text = title
            tvDetailSubtitle.text = subTitle
            tvDetailAuthorAndDate.text = authorAndDate
            tvDetailcontent.text = content
        }
    }
}
