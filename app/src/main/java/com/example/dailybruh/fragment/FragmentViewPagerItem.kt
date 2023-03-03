package com.example.dailybruh.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.MetricAffectingSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.adapters.bindImage
import com.example.dailybruh.calendar.parseDate
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.changeHeight

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
                publishedatPage.text =
                    "Опубликована: ${parseDate(news.articles[position].time)}"
                authorPage.text = "Автор: ${news.articles[position].author}"
                bindImage(urlPhoto, news.articles[position].image)
                descPage.text = getSpan()
        }
    }

    private fun getSpan(): Spannable {
        val string = news.articles[position].desc
        val spannable = SpannableStringBuilder(string)
        try {
            spannable.setSpan(
                RelativeSizeSpan(1.1F),
                0,
                string!!.indexOf(' '),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } catch (e: java.lang.NullPointerException) {
            return SpannableStringBuilder(" ")
        }
        return spannable
    }
}