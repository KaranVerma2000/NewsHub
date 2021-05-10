package com.example.newsforyou.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmark: BookmarkModel)

    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): LiveData<List<BookmarkModel>>

    @Query("DELETE FROM bookmark WHERE title=:title")
    fun removeBookmark(title: String)

    @Query("SELECT * FROM bookmark WHERE title=:title")
    fun checkBookmark(title: String): List<BookmarkModel>


}

