package com.example.dailybruh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.interfaces.ItemTouchHelperAdapter
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.Article
import com.example.dailybruh.dataclasses.News
import java.util.*

class NewsPageRecyclerAdapter(private val news: News): RecyclerView.Adapter<NewsPageRecyclerAdapter.ViewHolder>(),
    ItemTouchHelperAdapter {

    init {
        for(i in 0..itemCount) {
          //  _items.add(news.articles[i])
        }
    }

    private lateinit var binding: RecyclerItemNewsPageBinding
    private lateinit var context: Context
    private lateinit var _items: MutableList<Article>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerItemNewsPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.apply {
            holder.apply {
                titlePage.text = news.articles[position].title
                publishedatPage.text = "Опубликована: ${news.articles[position].time}"
                authorPage.text = "Автор: ${news.articles[position].author}"
                bindImage(urlPhoto,news.articles[position].image)
                descPage.text = news.articles[position].desc
            }
        }
    }

    override fun onItemMove(from: Int, to: Int) {
        Collections.swap(_items,from,to)
    }

    override fun getItemCount(): Int = 5

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)
}