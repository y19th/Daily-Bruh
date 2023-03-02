package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
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
                urlArticle.setOnClickListener {
                    urlArticle.setTextColor(requireContext().getColor(R.color.pressed_url_color))
                }
                bindImage(urlPhoto, news.articles[position].image)
                descPage.text = news.articles[position].desc
        }
    }
}