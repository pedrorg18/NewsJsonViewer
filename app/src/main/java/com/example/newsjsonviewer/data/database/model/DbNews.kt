package com.example.newsjsonviewer.data.database.model

import androidx.room.*
import com.example.newsjsonviewer.data.database.*

/**
 * Represents a news entity to store into / get from database.
 * It has an optional foreign key to cachedNews entity
 */
@Entity(
    tableName = TABLE_NAME_NEWS,
    foreignKeys = [
        ForeignKey(
            entity = CachedNews::class,
            parentColumns = [COLUMN_CACHED_NEWS_COUNTRY],
            childColumns = [COLUMN_FK_NEWS_COUNTRY],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(COLUMN_FK_NEWS_COUNTRY)
    ]

)
data class DbNews (
    val title: String,
    val description: String?,
    @ColumnInfo(name = COLUMN_NEWS_IMAGE_URL)
    val imageUrl: String?,
    val author: String?,
    val source: String?,
    @ColumnInfo(name = COLUMN_NEWS_PUBLISHED_AT)
    val publishedAt: Long?,
    val content: String?,
    @ColumnInfo(name = COLUMN_FK_NEWS_COUNTRY)
    var cachedNewsCountry: String? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NEWS_ID)
    var id: Int = 0

)