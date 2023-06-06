package com.example.dailybruh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.databinding.RecyclerItemSavedNewsBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.ifNull

class LikedArticlesRecyclerAdapter(private val pageArray: List<PageArticle>): RecyclerView.Adapter<LikedArticlesRecyclerAdapter.ViewHolder>() {

    private var _binding: RecyclerItemSavedNewsBinding? = null
    private val binding: RecyclerItemSavedNewsBinding get() = requireNotNull(_binding)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = RecyclerItemSavedNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(pageArray[position]) {
            binding.apply {
                header.text = this@with.header.ifNull("Без заголовка")
                authorPage.text = this@with.author.ifNull("Без автора")
                bindImage(urlPhoto, this@with.urlPhoto)
            }
        }
    }


    override fun getItemCount(): Int = pageArray.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()



    inner class ViewHolder(val binding: RecyclerItemSavedNewsBinding): RecyclerView.ViewHolder(binding.root)
}