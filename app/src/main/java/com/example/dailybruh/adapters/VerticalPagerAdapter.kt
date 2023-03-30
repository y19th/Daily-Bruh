package com.example.dailybruh.adapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dailybruh.database.Database
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.fragment.FragmentViewPagerItem


class VerticalPagerAdapter(private val news: News,
                           private val database: Database,
                           fragmentManager: FragmentManager,
                           lifecycle: Lifecycle
                           ) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int = news.total

    override fun createFragment(position: Int): Fragment = FragmentViewPagerItem(news,database,position)

}