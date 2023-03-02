package com.example.dailybruh.adapters
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.fragment.FragmentViewPagerItem


class VerticalPagerAdapter(private val news: News,
                           fragmentManager: FragmentManager,
                           lifecycle: Lifecycle
                           ) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment = FragmentViewPagerItem(news,position)

}