package com.example.dailybruh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News

class NewsPageRecyclerAdapter(private val news: News): RecyclerView.Adapter<NewsPageRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: RecyclerItemNewsPageBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerItemNewsPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.apply {
            holder.apply {
                titlePage.text = news.articles[position].title
                descPage.text = news.articles[position].desc
            }
        }
    }

    override fun getItemCount(): Int = 10

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)
}