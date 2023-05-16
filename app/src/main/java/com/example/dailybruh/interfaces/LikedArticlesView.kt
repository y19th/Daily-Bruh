package com.example.dailybruh.interfaces

import com.example.dailybruh.dataclasses.PageArticle

interface LikedArticlesView {
    fun setAdapter(pageArray: List<PageArticle>)
}