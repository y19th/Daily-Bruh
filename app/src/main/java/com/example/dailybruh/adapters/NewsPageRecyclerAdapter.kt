package com.example.dailybruh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.R
import com.example.dailybruh.calendar.parseDate
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News

class NewsPageRecyclerAdapter(private val news: News): RecyclerView.Adapter<NewsPageRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: RecyclerItemNewsPageBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerItemNewsPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       // val params = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        //binding.root.layoutParams = params
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            binding.apply {
                holder.apply {
                    titlePage.text = news.articles[position].title
                    publishedatPage.text =
                        parseDate(news.articles[position].time)
                    authorPage.text = news.articles[position].author
                    bindImage(urlPhoto, news.articles[position].image)
                    descPage.text = news.articles[position].desc
                }
            }
    }

    override fun getItemCount(): Int = news.total


    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)
}