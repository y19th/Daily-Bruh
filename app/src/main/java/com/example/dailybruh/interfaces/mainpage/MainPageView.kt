package com.example.dailybruh.interfaces.mainpage

import com.example.dailybruh.database.Database
import com.example.dailybruh.dataclasses.News

interface MainPageView {
    fun setNews(news: News, database: Database, likesMap: HashMap<String,String>,savesMap: HashMap<String,String>)
}