package com.example.newsjsonviewer.framework.network.model

import java.util.*

data class NewsEntity(val title: String,
                      val description: String?,
                      val urlToImage: String?,
                      val author: String?,
                      val source: SourceEntity?,
                      val publishedAt: Date?,
                      val content: String?)