package com.example.dailybruh.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseAdapter<T : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var _binding: T? = null
    val binding: T = requireNotNull(_binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
       return super.createViewHolder(parent,viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
    }

    override fun getItemCount(): Int = 0
}