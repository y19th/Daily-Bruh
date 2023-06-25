package com.example.dailybruh.interfaces.mainpage.pager

interface PagerItemPresenterInterface {

    fun getLikes()

    fun getSaves()
    fun isLiked()
    fun isSaved()

    fun changeLikes(long: Long)

    fun changeSaveState()
}