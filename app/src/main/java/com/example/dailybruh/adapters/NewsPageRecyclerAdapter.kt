package com.example.dailybruh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.RecyclerItemSavedNewsBinding
class NewsPageRecyclerAdapter(private val database: Database,
                              private val lifecycleOwner: LifecycleOwner,
                              private val totalLiked: Long
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
                holder.apply {
                        database.getArticleByPos(pos = position).observe(lifecycleOwner) {
                                header.text = it.header
                                bindImage(urlPhoto, it.urlPhoto)
                                authorPage.text = it.author
                        }
                }
            }
    }

    override fun getItemCount(): Int = totalLiked.toInt()


    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)
}