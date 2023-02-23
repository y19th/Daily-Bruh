package com.example.dailybruh.dataclasses

import com.squareup.moshi.Json
import java.io.Serializable

data class News(
    @Json(name = "totalResults")val total: Int,
    @Json(name = "status")val status: String,
    @Json(name = "articles")val articles: List<Article>): Serializable


data class Article(
    @Json(name = "author")val author: String?,
    @Json(name = "source")val source: Source,
    @Json(name = "url")val url: String?,
    @Json(name = "title")val title: String?,
    @Json(name = "publishedAt")val time: String?,
    @Json(name = "content")val content: String?,
    @Json(name = "urlToImage")val image: String?,
    @Json(name = "description")val desc: String?)

data class Source(
    @Json(name = "id")val id: String?,
    @Json(name = "name")val name: String?)