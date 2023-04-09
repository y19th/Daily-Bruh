package com.example.dailybruh.const

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.dataclasses.News


const val NEWS_DATA = "WQEekfgw3284fewi"
const val DATABASE = "ewqr325fhbrt"
const val VERIFICATION_ID = "qrktgrwek5212dsqadf"
const val AUTH_OPTIONS = "qwetgerwgrtgre3252"

internal val constNews: MutableLiveData<News> by lazy {
    MutableLiveData<News>()
}
