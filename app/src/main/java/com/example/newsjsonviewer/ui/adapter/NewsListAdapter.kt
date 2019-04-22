package com.example.newsjsonviewer.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.newsjsonviewer.R
import com.example.newsjsonviewer.ui.domain.model.News
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
        vh.content.text = item.content
        // FIXME layout image by it's URL, placeholder by now
        vh.image.setImageResource(R.drawable.ic_launcher_foreground)
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.tvListItemTitle)
    val content: TextView = view.findViewById(R.id.tvListItemContent)
    val image: ImageView = view.findViewById(R.id.ivListItem)
}