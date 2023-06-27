package com.example.dailybruh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dailybruh.databinding.RecyclerItemSavedArticlesBinding
import com.example.dailybruh.dataclasses.PageArticle
import com.example.dailybruh.extension.showTabOnClick

class SavedArticlesRecyclerAdapter(private val pageArray: List<PageArticle>) : BaseAdapter<RecyclerItemSavedArticlesBinding>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecyclerItemSavedArticlesBinding> {
        _binding = RecyclerItemSavedArticlesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<RecyclerItemSavedArticlesBinding>,
        position: Int
    ) {
        with(pageArray[position]) {
            binding.apply {
                authorPage.text = this@with.author
                headerPage.text = this@with.header
                descPage.text = this@with.description
                bindImage(urlPhoto, this@with.urlPhoto)
                mainLayout.showTabOnClick(this@with.urlPage)
            }
        }
    }

    override fun getItemCount(): Int = pageArray.size
}