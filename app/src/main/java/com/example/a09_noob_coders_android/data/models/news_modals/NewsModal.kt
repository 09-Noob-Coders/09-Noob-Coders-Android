package com.example.a09_noob_coders_android.data.models.news_modals

data class NewsModal(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)