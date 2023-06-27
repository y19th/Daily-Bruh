package com.example.dailybruh.dataclasses

data class PageArticle(
    val id: String,
    val header: String,
    val urlPhoto: String?,
    val urlPage: String,
    val author: String? = "Без автора",
    val description: String? = "..."
)
