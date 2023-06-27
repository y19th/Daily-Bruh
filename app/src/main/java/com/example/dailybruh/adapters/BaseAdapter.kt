package com.example.dailybruh.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseAdapter<T : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var _binding: T? = null
    val binding: T get() = requireNotNull(_binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
       return super.createViewHolder(parent,viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
    }

    override fun getItemCount(): Int = 0
    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()
}