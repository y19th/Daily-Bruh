package com.example.dailybruh.fragment

import android.R.attr.bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dailybruh.adapters.bindImage
import com.example.dailybruh.calendar.parseDate
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News


class FragmentViewPagerItem(
    private val news: News,
    private val position: Int
): Fragment() {

    private var _binding: RecyclerItemNewsPageBinding? = null
    private val binding: RecyclerItemNewsPageBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecyclerItemNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
                titlePage.text = news.articles[position].title
                publishedatPage.text = parseDate(news.articles[position].time)
                authorPage.text = news.articles[position].author
                bindImage(urlPhoto, news.articles[position].image)
                descPage.text = resizeDueTextLength()
        }
    }

    private fun resizeDueTextLength(): String {
        //val string = resizeDescription()
        val string = news.articles[position].desc!!
        resizeTitle()
        return string
    }
    private fun resizeDescription(): String {
        val str = news.articles[position].desc
        return if(str!!.length > 150) str.substring(0, str.indexOf(" ", 130)).plus("...")
            else return str
    }
    private fun resizeTitle() {
        if(news.articles[position].title!!.length > 50)binding.titlePage.textSize = 24F
        binding.titlePage.apply {
            textSize = when (news.articles[position].title!!.length) {
                in 0..50 -> 32F
                in 50..75 -> 28F
                in 75..100 -> 24F
                else -> 20F
            }
        }

    }
}