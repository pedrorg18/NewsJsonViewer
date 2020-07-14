package com.example.newsjsonviewer.data.database.dao

import androidx.room.*
import com.example.newsjsonviewer.data.database.model.CachedNews
import com.example.newsjsonviewer.data.database.model.DbNews
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface CachedNewsDao {

    /**
     * Inserts cached news, then news list in the same transaction
     *
     * @return true if both inserts succeed
     */
    @Transaction
    fun insertCachedNewsAndNewsList(cachedNews: CachedNews, newsList: List<DbNews>): Boolean =
        try {
            insert(cachedNews)
            insertNewsList(newsList)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cachedNews: CachedNews)

    @Insert
    fun insertNewsList(newsList: List<DbNews>)

    /**
     * Fetches cachedNews entity plus its associated news entities
     */
    @Transaction
    @Query("SELECT * FROM cached_news WHERE country = :country")
    fun findCachedNewsAndDbNewsByCountry(country: String): Maybe<CachedNewsAndDbNews>

    @Delete
    fun delete(cachedNews: CachedNews): Completable

    /**
     * Represents a CachedNews entity including a list of its children DbNews
     */
    class CachedNewsAndDbNews {
        @Embedded
        lateinit var cachedNews: CachedNews

        @Relation(parentColumn = "country", entityColumn = "cached_news_country")
        lateinit var dbNewsList: List<DbNews>
    }
}