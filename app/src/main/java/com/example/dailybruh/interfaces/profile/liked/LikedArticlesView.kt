package com.example.dailybruh.interfaces.profile.liked

import com.example.dailybruh.dataclasses.PageArticle

interface LikedArticlesView {
    fun setAdapter(pageArray: List<PageArticle>)
}