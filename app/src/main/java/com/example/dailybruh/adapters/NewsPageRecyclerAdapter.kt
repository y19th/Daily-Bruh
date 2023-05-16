package com.example.dailybruh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.databinding.RecyclerItemSavedNewsBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.presenter.LikedArticlesPresenter

class NewsPageRecyclerAdapter(private val presenter: LikedArticlesPresenter,
                              private val pageArray: List<PageArticle>
): RecyclerView.Adapter<NewsPageRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: RecyclerItemSavedNewsBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerItemSavedNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            binding.apply {
                header.text = pageArray[position].header
                authorPage.text = pageArray[position].author
                bindImage(urlPhoto,pageArray[position].urlPhoto)
            }
    }

    override fun getItemCount(): Int = presenter.total.toInt()


    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)
}