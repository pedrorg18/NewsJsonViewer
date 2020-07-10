package com.example.newsjsonviewer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.newsjsonviewer.data.database.model.DbNews
import io.reactivex.Completable

@Dao
interface NewsDao {
    @Insert
    fun insert(newsList: List<DbNews>): Completable
}