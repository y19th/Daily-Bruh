package com.example.dailybruh.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailybruh.dataclasses.Article
import com.example.dailybruh.dataclasses.News
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private const val BASE_URL = "https://newsapi.org/v2/"
private const val API_KEY = "980de69a014b4a08ad7237fdd89e2545"
private var url: String? = null


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET suspend fun getNews(@Url url: String): News
}

fun setURLtoGetRequest(addUrl: String) {
    url = "$BASE_URL$addUrl&apiKey=$API_KEY"
}

object NewsApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}

class MoshiParse : ViewModel() {
    private val _status = MutableLiveData<String>()
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news
    val status: LiveData<String> = _status


    fun getNews(addUrl: String) {
        viewModelScope.launch {
            try {
                setURLtoGetRequest(addUrl)
                _news.value = NewsApi.retrofitService.getNews(url!!)
                _status.value = "ok"
            } catch (e : Exception) {
                _status.value = e.message.toString()
            }
        }
    }

}
