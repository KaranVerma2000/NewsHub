package com.example.newsforyou.Model

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)