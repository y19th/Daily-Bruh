package com.example.dailybruh.interfaces.mainpage.pager

import com.example.dailybruh.fragment.FragmentViewPagerItem
import com.example.dailybruh.interfaces.StandardView

interface PagerItemView: StandardView<FragmentViewPagerItem> {

    fun setLikes(count : Long)

    fun setSaves()
    fun checkIsSaved(isSaved: Boolean)
    fun checkIsLiked(isLiked: Boolean)
}