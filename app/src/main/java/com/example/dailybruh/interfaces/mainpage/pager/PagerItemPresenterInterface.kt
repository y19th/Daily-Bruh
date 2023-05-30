package com.example.dailybruh.interfaces.mainpage.pager

interface PagerItemPresenterInterface {

    fun getLikes()
    fun isLiked()

    fun changeLikes(long: Long)
}