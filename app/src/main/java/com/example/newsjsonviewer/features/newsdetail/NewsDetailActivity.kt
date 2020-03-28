package com.example.newsjsonviewer.features.newsdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.globals.BaseActivity
import com.example.newsjsonviewer.ui.extensions.hide
import com.example.newsjsonviewer.ui.image.loadImage
import kotlinx.android.synthetic.main.activity_news_detail.*

const val NEWS_TO_SHOW_DETAIL_EXTRA = "news_to_show_detail"

class NewsDetailActivity : BaseActivity() {

    private val viewModel by lazy { initViewModel() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        observeViewModel()

        supportPostponeEnterTransition()

        initListeners()

        viewModel.onEvent(
            NewsDetailEvent.LoadDetailScreen(
                intent.getSerializableExtra(NEWS_TO_SHOW_DETAIL_EXTRA) as News)
            )
    }

    private fun initViewModel(): NewsDetailViewModel {
        return  NewsDetailViewModelFactory()
            .create(NewsDetailViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.viewStateLiveData.observe(this, Observer { viewState ->
            render(viewState)
        })
    }

    private fun render(viewState: NewsDetailViewState) {
        when(viewState) {
            is NewsDetailViewState.Content -> renderContent(viewState.content)
        }

    }

    private fun renderContent(content: NewsDetailViewStateContent) {
        with(content) {
            // add listener for the animation not to occur until the image is loaded, as Glide has
            // an issue with animation transitions when not yet loaded
            loadImage(
                ivDetail,
                content.imageUrl,
                object : RequestListener<Drawable> {
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
            tvDetailcontent.text = this.content
        }
    }

    private fun initListeners() {
        imageDetailGoBack.setOnClickListener {
            it.hide()
            onBackPressed()
        }
    }

}