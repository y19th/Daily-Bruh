package com.example.dailybruh.dagger

import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.recentRequest
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Module
class NewsModule {
    @[Provides Singleton]
    fun provideNewsViewModel(request: Request): NewsViewModel {
        return NewsViewModel().also { it.getNews(request.request) }
    }
    @Provides
    fun providesNewsFlow(newsViewModel: NewsViewModel) : StateFlow<News> {
        return newsViewModel.news
    }

    @Provides
    fun providesRequest() : Request {
        return recentRequest
    }
}