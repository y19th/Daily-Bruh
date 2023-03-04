package com.example.dailybruh.fragment

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                publishedatPage.text =
                    "Опубликована: ${parseDate(news.articles[position].time)}"
                authorPage.text = "Автор: ${news.articles[position].author}"
                bindImage(urlPhoto, news.articles[position].image)
                descPage.text = getSpan()
        }
    }

    private fun getSpan(): Spannable {
        val string = substr()
        val spannable = SpannableStringBuilder(string)
        try {
            spannable.setSpan(
                RelativeSizeSpan(1.1F),
                0,
                string.indexOf(' '),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } catch (e: java.lang.NullPointerException) {
            return SpannableStringBuilder(" ")
        }
        return spannable
    }
    private fun substr(): String {
        val str = news.articles[position].desc
        return if(str!!.length > 200) str.substring(0, str.indexOf(" ", 200)).plus("...")
            else return str
    }
}