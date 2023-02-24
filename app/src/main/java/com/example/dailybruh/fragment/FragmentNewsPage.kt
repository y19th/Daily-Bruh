package com.example.dailybruh.fragment

import android.app.ActionBar.LayoutParams
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.const.NEWS_DATA
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.changeHeight
import com.example.dailybruh.extension.changeParams

class FragmentNewsPage : Fragment() {

    private lateinit var binding: FragmentNewsPageBinding
    private lateinit var news: News

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
            recyclerNewsPage.apply {
               //changeHeight(qwe)
                layoutManager = LinearLayoutManager(context)
                adapter = NewsPageRecyclerAdapter(news)
            }
        }
    }
}