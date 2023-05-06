package com.example.dailybruh.const

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.dataclasses.News


const val NEWS_DATA = "WQEekfgw3284fewi"
const val DATABASE = "ewqr325fhbrt"
const val VERIFICATION_ID = "qrktgrwek5212dsqadf"
const val AUTH_OPTIONS = "qwetgerwgrtgre3252"
const val STANDARD_PHONE = "00000000000"
const val API_KEY = "980de69a014b4a08ad7237fdd89e2545"
const val BASE_URL = "https://newsapi.org/v2/"

internal val constNews: MutableLiveData<News> by lazy {
    MutableLiveData<News>()
}
