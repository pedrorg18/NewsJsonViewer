package com.example.newsjsonviewer.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        if(item.imageUrl != null)
            loadImage(vh.image, item.imageUrl)
        //TODO else load placeholder

    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.tvListItemTitle)
    val description: TextView = view.findViewById(R.id.tvListItemContent)
    val image: ImageView = view.findViewById(R.id.ivListItem)
}