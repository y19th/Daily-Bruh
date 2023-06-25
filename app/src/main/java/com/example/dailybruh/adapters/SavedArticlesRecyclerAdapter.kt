package com.example.dailybruh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.dailybruh.databinding.RecyclerItemSavedArticlesBinding

class SavedArticlesRecyclerAdapter : BaseAdapter<RecyclerItemSavedArticlesBinding>() {

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
        binding.apply {

        }
    }

    override fun getItemCount(): Int = 0 //TODO()

    override fun getItemId(position: Int): Long = position.toLong()
}