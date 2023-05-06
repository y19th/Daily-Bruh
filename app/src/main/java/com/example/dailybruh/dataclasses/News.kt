package com.example.dailybruh.dataclasses

import com.squareup.moshi.Json
import java.io.Serializable

data class News(
    @Json(name = "totalResults")val total: Int = 0,
    @Json(name = "status")val status: String = "null",
    @Json(name = "articles")val articles: List<Article> = listOf()
): Serializable


data class Article(
    var id: String = "-",
    @Json(name = "author")val author: String?,
    @Json(name = "source")val source: Source,
    @Json(name = "url")val url: String?,
    @Json(name = "title")val title: String?,
    @Json(name = "publishedAt")val time: String?,
    @Json(name = "content")val content: String?,
    @Json(name = "urlToImage")val image: String?,
    @Json(name = "description")val desc: String?) : Serializable

data class Source(
    @Json(name = "id")val id: String?,
    @Json(name = "name")val name: String?) : Serializable