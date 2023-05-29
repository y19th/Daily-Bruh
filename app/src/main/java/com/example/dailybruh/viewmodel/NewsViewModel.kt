package com.example.dailybruh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailybruh.const.API_KEY
import com.example.dailybruh.const.BASE_URL
import com.example.dailybruh.const.constNews
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.web.NewsApi
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class NewsViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _news = MutableStateFlow(News())
    val news: StateFlow<News> = _news.asStateFlow()


    fun getNews(addUrl: String) {
        viewModelScope.launch(Dispatchers.Main + CoroutineName("getNews")) {
            try {
                val result = NewsApi.retrofitService.getNews(getURL(addUrl))
                result.setId()
                _news.collect {
                    _news.update { currentState ->
                        currentState.copy(
                            total = result.total,
                            status = result.status,
                            articles = result.articles
                        )
                    }
                    this.cancel()
                }
            } catch (e : Exception) {
                _status.value = e.message
            }
        }
    }

    private fun getURL(addUrl: String) = "$BASE_URL$addUrl&apiKey=$API_KEY"

    private fun News.setId() {
        var count = 0
        val lock = ReentrantLock()
        viewModelScope.launch {
            lock.withLock {
                for(item in this@setId.articles) {
                    item.apply {
                        id = "${title?.subSequence(0,3).toString()}${count++}_${time}"
                    }
                }
            }
        }.invokeOnCompletion {
            constNews.value = this
        }
    }
}