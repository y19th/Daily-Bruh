package com.example.dailybruh.interfaces

interface PagerItemPresenterInterface {

    fun getLikes()
    fun isLiked()

    fun changeLikes(long: Long)
}