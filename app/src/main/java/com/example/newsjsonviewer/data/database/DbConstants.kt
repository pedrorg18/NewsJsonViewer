package com.example.newsjsonviewer.data.database

// cached news
const val TABLE_NAME_CACHED_NEWS = "cached_news"
const val COLUMN_CACHED_NEWS_COUNTRY = "country"

// news
const val TABLE_NAME_NEWS = "news"
const val COLUMN_NEWS_IMAGE_URL = "image_url"
const val COLUMN_NEWS_PUBLISHED_AT = "published_at"
const val COLUMN_FK_NEWS_COUNTRY = "cached_news_country"
const val COLUMN_NEWS_ID = "news_id"