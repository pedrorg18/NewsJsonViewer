package com.example.newsjsonviewer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsjsonviewer.data.database.COLUMN_CACHED_NEWS_COUNTRY
import com.example.newsjsonviewer.data.database.TABLE_NAME_CACHED_NEWS

/**
 * Represents a block of news obtained from backend and saved as cache
 *
 * @param country the country for which the news were queried
 * @param timestamp time when these news were stored in the db
 */
@Entity(tableName = TABLE_NAME_CACHED_NEWS)
data class CachedNews(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_CACHED_NEWS_COUNTRY)
    val country: String,
    val timestamp: Long
)