package com.example.dailybruh.interfaces.profile.saved

import com.example.dailybruh.dataclasses.PageArticle

interface SavedArticlesView {

    fun setAdapter(pageArray: List<PageArticle>)
}