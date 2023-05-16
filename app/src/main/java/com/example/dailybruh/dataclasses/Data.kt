package com.example.dailybruh.dataclasses

import androidx.lifecycle.MutableLiveData

data class DataArticle(val id: String ,val header: String,val urlPhoto : String,val urlPage: String,
                       val author: String,var status: MutableLiveData<Int> = MutableLiveData(0)
)

data class PageArticle(
    val id: String,
    val header: String,
    val urlPhoto: String?,
    val urlPage: String,
    val author: String? = "Без автора"
)


data class ArticleLikes(val id: String, var likes: Long, var status: MutableLiveData<Int> = MutableLiveData(0))