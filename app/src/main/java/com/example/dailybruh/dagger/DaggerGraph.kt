package com.example.dailybruh.dagger

import android.content.Context
import com.example.dailybruh.database.Database
import com.example.dailybruh.fragment.FragmentNewsPage
import com.example.dailybruh.viewmodel.NewsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@[Singleton Component(modules = [MainModule::class])]
interface MainComponent {
    val newsViewModel: NewsViewModel

    val database: Database

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build() : MainComponent

    }
    fun inject(activity: FragmentNewsPage)
}

@Module(includes = [NewsModule::class, DatabaseModule::class])
class MainModule