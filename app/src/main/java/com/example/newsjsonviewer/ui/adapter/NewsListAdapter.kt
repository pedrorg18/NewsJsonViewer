package com.example.newsjsonviewer.ui.adapter

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.example.newsjsonviewer.ui.NEWS_TO_SHOW_DETAIL_EXTRA
import com.example.newsjsonviewer.ui.NewsDetailActivity
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.ui.image.loadImage
import kotlin.properties.Delegates

class NewsListAdapter : RecyclerView.Adapter<ViewHolder>() {

    // Init empty list, refresh adapter when changed
    var items: List<News> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false))
    }

    override fun getItemCount() =
        items.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val item = items[position]
        vh.title.text = item.title
        vh.description.text = item.description ?: ""
        loadImage(vh.image, item.imageUrl!!)

        vh.rootView.setOnClickListener {
            val ctx = vh.rootView.context
            val intent = Intent(ctx, NewsDetailActivity::class.java)
            intent.putExtra(NEWS_TO_SHOW_DETAIL_EXTRA, item)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(ctx as Activity, vh.image,
                ViewCompat.getTransitionName(vh.image)!!)
            ctx.startActivity(intent, options.toBundle())
        }
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val rootView = view
    val title: TextView = view.findViewById(R.id.tvListItemTitle)
    val description: TextView = view.findViewById(R.id.tvListItemContent)
    val image: ImageView = view.findViewById(R.id.ivListItem)
}