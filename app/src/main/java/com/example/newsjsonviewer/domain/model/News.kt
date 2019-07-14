package com.example.newsjsonviewer.domain.model

import java.io.Serializable
import java.util.*

data class News (
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val author: String?,
    val source: String?,
    val publishedAt: Date?,
    val content: String?
    ): Serializable