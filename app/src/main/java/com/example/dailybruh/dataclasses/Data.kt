package com.example.dailybruh.dataclasses

import androidx.lifecycle.MutableLiveData

data class DataArticle(val id: String ,val header: String,val urlPhoto : String,val urlPage: String,
                       val author: String,var status: MutableLiveData<Int> = MutableLiveData(0)
)
data class ArticleLikes(val id: String,val num: Long,var status: MutableLiveData<Int> = MutableLiveData(0))