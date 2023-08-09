package com.example.dailybruh.dagger

import android.content.Context
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.fragment.FragmentNewsPage
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.recentRequest
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@[Singleton Component(modules = [MainModule::class])]
interface MainComponent {
    val daggerTest: Test

    val newsViewModel: NewsViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build() : MainComponent

    }
    fun inject(activity: FragmentNewsPage)
}

@Module(includes = [NewsModule::class])
class MainModule {
    @Provides
    fun providesTest(): Test {
        return Test()
    }

}

@Module
class NewsModule {
    @[Provides Singleton]
    fun provideNewsViewModel(request: Request): NewsViewModel {
        return NewsViewModel().also { it.getNews(request.request) }
    }
    @Provides
    fun providesNewsFlow(newsViewModel: NewsViewModel, request: Request) : StateFlow<News> {
        newsViewModel.getNews(request.request)
        return newsViewModel.news
    }

    @Provides
    fun providesRequest() : Request {
        return recentRequest
    }
}



class Test {
    override fun toString(): String {
        return "test"
    }
}