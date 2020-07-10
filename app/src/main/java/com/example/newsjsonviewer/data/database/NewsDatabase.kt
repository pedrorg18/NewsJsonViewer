package com.example.newsjsonviewer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsjsonviewer.data.database.dao.CachedNewsDao
import com.example.newsjsonviewer.data.database.dao.NewsDao
import com.example.newsjsonviewer.data.database.model.CachedNews
import com.example.newsjsonviewer.data.database.model.DbNews
import javax.inject.Singleton

@Singleton
@Database(
    version = 1,
    entities = [CachedNews::class, DbNews::class]
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun cachedNewsDao(): CachedNewsDao
    abstract fun newsDao(): NewsDao

    companion object {
        private var INSTANCE: NewsDatabase? = null
        fun getInstance(ctx: Context) =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE 
                        ?: buildDatabase(ctx)
                            .also { INSTANCE = it }
                }

        private fun buildDatabase(ctx: Context): NewsDatabase =
            Room.databaseBuilder(
                ctx.applicationContext, NewsDatabase::class.java, "news-database"
            ).build()
    }

}