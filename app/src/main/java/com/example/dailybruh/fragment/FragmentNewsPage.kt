package com.example.dailybruh.fragment

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.dailybruh.R
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.const.NEWS_DATA
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.enableView
import com.example.dailybruh.manager.LinearRecyclerManager

class FragmentNewsPage : Fragment() {

    private lateinit var binding: FragmentNewsPageBinding
    private lateinit var news: News
    private var pos = 0
    private var temp = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        news = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(NEWS_DATA,News::class.java) as News
        } else {
            arguments?.getSerializable(NEWS_DATA) as News
        }
        binding.apply {
            viewpagerMain.apply {
                adapter = VerticalPagerAdapter(news,parentFragmentManager,lifecycle)

            }
            navView.y +=60
            navMenuButton.setOnClickListener {
                mainLayout.openDrawer(GravityCompat.START)
            }
        }
    }


}