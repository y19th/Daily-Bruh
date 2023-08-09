package com.example.dailybruh.interfaces.mainpage.pager

interface PagerItemView {

    fun setLikes(count : Long)

    fun setSaves()
    fun checkIsSaved(isSaved: Boolean)
    fun checkIsLiked(isLiked: Boolean)
}