package com.example.newsforyou.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
class BookmarkModel(
    var author: String? = "",
    var name: String? = "",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var urlToImage: String? = "",
    var publishedAt: String? = "",
    var content: String? = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

