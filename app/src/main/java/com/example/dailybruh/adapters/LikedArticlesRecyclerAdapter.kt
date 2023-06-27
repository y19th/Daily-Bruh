package com.example.dailybruh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dailybruh.databinding.RecyclerItemLikedNewsBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.ifNull
import com.example.dailybruh.extension.showTabOnClick

class LikedArticlesRecyclerAdapter(private val pageArray: List<PageArticle>): BaseAdapter<RecyclerItemLikedNewsBinding>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecyclerItemLikedNewsBinding> {
        _binding = RecyclerItemLikedNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<RecyclerItemLikedNewsBinding>,
        position: Int
    ) {
        with(pageArray[position]) {
            binding.apply {
                header.text = this@with.header.ifNull("Без заголовка")
                authorPage.text = this@with.author
                bindImage(urlPhoto, this@with.urlPhoto)
                mainLayout.showTabOnClick(this@with.urlPage)
            }
        }
    }


    override fun getItemCount(): Int = pageArray.size
}