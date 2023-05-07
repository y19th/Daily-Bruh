package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailybruh.R
import com.example.dailybruh.adapters.NewsPageRecyclerAdapter
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentDilaogLikedArticlesBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.viewmodel.DatabaseViewModel

class FragmentProfileLikedArticles : StandardFragment<FragmentDilaogLikedArticlesBinding>() {

    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var database : Database
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDilaogLikedArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database = databaseViewModel.withLifecycle(lifecycleOwner = viewLifecycleOwner).value
        binding.apply {
            recyclerview.apply {
                database.totalLiked.observe(viewLifecycleOwner) {
                    when (it) {
                        0L -> binding.errorLayout.visibility = View.VISIBLE
                        else -> binding.errorLayout.visibility = View.GONE
                    }
                    adapter = NewsPageRecyclerAdapter(
                        database,
                        viewLifecycleOwner,
                        database.totalLiked.value!!
                    )
                    layoutManager = LinearLayoutManager(context)
                }
            }
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.liked_articles_to_profile)
            }
        }
    }
}