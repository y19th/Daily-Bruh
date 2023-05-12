package com.example.dailybruh.interfaces

import com.example.dailybruh.database.Database
import com.example.dailybruh.dataclasses.News

interface MainPageView {

    fun setNews(news: News, database: Database, likesMap: HashMap<String,String>)
}