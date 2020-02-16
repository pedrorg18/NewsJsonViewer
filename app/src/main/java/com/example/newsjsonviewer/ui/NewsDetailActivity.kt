package com.example.newsjsonviewer.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.data.repository.NewsRepository
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.app.NewsApplication
import com.example.newsjsonviewer.ui.image.loadImage
import com.example.newsjsonviewer.ui.model.DetailActivityModel
import com.example.newsjsonviewer.ui.viewmodel.NewsDetailViewModel
import kotlinx.android.synthetic.main.activity_news_detail.*

const val NEWS_TO_SHOW_DETAIL_EXTRA = "news_to_show_detail"

class NewsDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val repo = (application as NewsApplication).getComponent().newsRepository()
        val vm = NewsDetailViewModelFactory(repo).create(NewsDetailViewModel::class.java)

        vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        title = ""

        initAndObserveViewModel()

        supportPostponeEnterTransition()
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
            // add listener for the animation not to occur until the image is loaded, as Glide has
            // an issue with animation transitions when not yet loaded
            loadImage(ivDetail, news.imageUrl, object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

            })

            tvDetailTitle.text = title
            tvDetailSubtitle.text = subTitle
            tvDetailAuthorAndDate.text = authorAndDate
            tvDetailcontent.text = content
        }
    }

}

class NewsDetailViewModelFactory(private val repo: NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = NewsDetailViewModel(repo) as T
}